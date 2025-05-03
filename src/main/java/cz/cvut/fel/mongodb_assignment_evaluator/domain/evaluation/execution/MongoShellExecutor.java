package cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.execution;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.StudentSubmission;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type.QueryToken;
import cz.cvut.fel.mongodb_assignment_evaluator.infrastructure.config.MongoProperties;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;


@Component
public class MongoShellExecutor {
    private final static String QUERY_END_ACK = "<<END_OF_QUERY>>";

    private final MongoProperties mongoProperties;
    private final ProcessBuilder processBuilder;

    private final BlockingQueue<String> outputQueue;

    private Process process;
    private BufferedWriter consoleWriter;
    private Thread readerThread;


    public MongoShellExecutor(MongoProperties mongoProperties) {
        this.mongoProperties = mongoProperties;
        this.processBuilder = new ProcessBuilder(List.of(
                "mongosh",
                "mongodb://" + mongoProperties.getUsername() + ":" + mongoProperties.getPassword() + "@" +
                        mongoProperties.getHost() + ":" + mongoProperties.getPort() + "/" + mongoProperties.getDatabase(),
                "--quiet"
        )
        );
        processBuilder.redirectErrorStream(true);
        this.outputQueue = new LinkedBlockingQueue<>();
    }

    public void startMongoShell() throws IOException {
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
    }

    public void executeQueries(StudentSubmission submission) throws IOException, InterruptedException {
        if (process == null || !process.isAlive()) {
            startMongoShell();
        }

        sendCommand("db.getCollectionNames().forEach(function(collName) {db[collName].drop();});");
        consoleWriter.flush();
        outputQueue.clear();

        List<QueryToken> queryTokens = submission.getQueryList();
        for (QueryToken query : queryTokens) {
            String queryText = query.getQuery();
            submission.addLog(Level.INFO, "Executing query: " + queryText);
            sendCommand(queryText);
            sendCommand("print(\"" + QUERY_END_ACK + "\")");
        }
        consoleWriter.flush();

        String databaseName = mongoProperties.getDatabase();

        int receivedACKs = 0;
        int lastFetchedId = -1;
        QueryToken lastQueryToken = null;
        while (receivedACKs < queryTokens.size()) {
            if (receivedACKs != lastFetchedId) {
                lastQueryToken = queryTokens.get(receivedACKs);
                lastFetchedId = receivedACKs;
                submission.addLog(Level.INFO, "Collecting output from " + lastQueryToken.getQuery() + " execution");
            }
            String line = outputQueue.take();
            if (line.contains(QUERY_END_ACK)) {
                receivedACKs++;
            } else if (!line.isBlank()) {
                line = line.startsWith(databaseName) ? line.substring(databaseName.length() + 2) : line;
                lastQueryToken.addExecutionLog(line);
            }
        }
    }

    public void exitMongoShell() throws IOException, InterruptedException {
        sendCommand("db.getCollectionNames().forEach(function(collName) {db[collName].drop();});");
        consoleWriter.flush();

        sendCommand("exit");
        consoleWriter.flush();

        process.destroyForcibly();
        readerThread.join();
    }

    private void sendCommand(String cmd) throws IOException {
        consoleWriter.write(cmd);
        consoleWriter.newLine();
    }
}
