package cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.execution;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.enums.RegularExpressions;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.exceptions.MongoShellTimeOut;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.StudentSubmission;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type.QueryToken;
import cz.cvut.fel.mongodb_assignment_evaluator.infrastructure.config.MongoProperties;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Component
public class MongoShellExecutor {
    private final static String QUERY_END_ACK = "<<END_OF_QUERY>>";
    private final Pattern warningErrorPattern;

    private final MongoProperties mongoProperties;
    private final ProcessBuilder processBuilder;

    private final BlockingQueue<String> outputQueue;

    private Process process;
    private BufferedWriter consoleWriter;
    private Thread readerThread;


    public MongoShellExecutor(MongoProperties mongoProperties) {
        this.warningErrorPattern = Pattern.compile(RegularExpressions.MONGO_SHELL_STDERR.getRegex());
        this.mongoProperties = mongoProperties;
        this.processBuilder = new ProcessBuilder(List.of(
                "mongosh",
                "mongodb://" + mongoProperties.getUsername() + ":" + mongoProperties.getPassword() + "@" +
                        mongoProperties.getHost() + ":" + mongoProperties.getPort() + "/" + mongoProperties.getDatabase()
        ));
        processBuilder.redirectErrorStream(true);
        this.outputQueue = new LinkedBlockingQueue<>();
    }

    public void startMongoShell() throws IOException, InterruptedException {
        process = processBuilder.start();
        consoleWriter = new BufferedWriter(new OutputStreamWriter(process.getOutputStream(), StandardCharsets.UTF_8));
        readerThread = new Thread(() -> {
            try (BufferedReader consoleReader = new BufferedReader(
                    new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8)
            )) {
                String line;
                while ((line = consoleReader.readLine()) != null) {
                    outputQueue.put(line);
                }
            } catch (IOException | InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        readerThread.start();
        outputQueue.clear();

        sendCommand("print(\"init\")");

        long deadline = System.currentTimeMillis() + mongoProperties.getTimeout();
        while (true) {
            long timeLeft = deadline - System.currentTimeMillis();
            if (timeLeft <= 0) {
                throw new MongoShellTimeOut(mongoProperties.getTimeout(), "Most likely incorrect database settings");
            }
            String line = outputQueue.poll(timeLeft, TimeUnit.MILLISECONDS);
            if (line != null && line.contains("init")) {
                break;
            }
        }
        outputQueue.clear();
    }

    public void executeQueries(StudentSubmission submission) throws IOException, InterruptedException {
        if (process == null || !process.isAlive()) {
            startMongoShell();
        }

        sendCommand("db.getCollectionNames().forEach(function(collName) {db[collName].drop();});");
        outputQueue.clear();

        String databaseName = mongoProperties.getDatabase();
        boolean errorWarningOccurred = false;
        StringBuilder messageBuilder = new StringBuilder();
        List<QueryToken> queryTokens = submission.getQueryList();
        for (QueryToken query : queryTokens) {
            String queryText = query.getQuery();
            submission.addLog(Level.INFO, "Executing query: " + queryText);
            sendCommand(queryText);
            sendCommand("print(\"" + QUERY_END_ACK + "\")");
            long deadline = System.currentTimeMillis() + mongoProperties.getTimeout();
            while (true) {
                long timeLeft = deadline - System.currentTimeMillis();
                if (timeLeft <= 0) {
                    query.addExecutionLog("Query has timed out (" + mongoProperties.getTimeout() + " ms)");
                    submission.addLog(Level.SEVERE, "Time out (" + mongoProperties.getTimeout() + " ms) for query: " + queryText);
                    break;
                }
                String line = outputQueue.poll(timeLeft, TimeUnit.MILLISECONDS);
                if (line != null) {
                    if (line.contains(QUERY_END_ACK)) {
                        String message = messageBuilder.toString();
                        messageBuilder.setLength(0);
                        query.addExecutionLog(message);
                        if (errorWarningOccurred) {
                            submission.addLog(Level.SEVERE, queryText + ":" + System.lineSeparator() + message);
                            errorWarningOccurred = false;
                        }
                        break;
                    } else if (!line.isBlank()) {
                        line = line.startsWith(databaseName) ? line.substring(databaseName.length() + 2) : line;
                        if (messageBuilder.isEmpty()) {
                            Matcher stderrMatcher = warningErrorPattern.matcher(line);
                            if (stderrMatcher.matches()) {
                                errorWarningOccurred = true;
                            }
                        }
                        messageBuilder.append(line)
                                .append(System.lineSeparator());
                    }
                }
            }
        }
    }

    public void exitMongoShell() throws IOException, InterruptedException {
        sendCommand("db.getCollectionNames().forEach(function(collName) {db[collName].drop();});");

        sendCommand("exit");

        process.destroyForcibly();
        readerThread.join();
    }

    private void sendCommand(String cmd) throws IOException {
        consoleWriter.write(cmd);
        consoleWriter.newLine();
        consoleWriter.flush();
    }
}
