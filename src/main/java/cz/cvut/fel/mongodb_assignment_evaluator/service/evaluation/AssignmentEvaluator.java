package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation;

import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.CriteriaChecker;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.parser.ScriptParser;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.query.Query;
import lombok.extern.java.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Log
public class AssignmentEvaluator {
    public static void evaluate(String rootDirectoryPath) {
        try {
            File rootDirectory = new File(rootDirectoryPath);
            if (!rootDirectory.isDirectory()) {
                log.severe("Root directory is not a directory: " + rootDirectoryPath);
                return;
            }

            File[] subfolders = rootDirectory.listFiles();
            if (subfolders == null) {
                log.severe("No subfolders found in directory: " + rootDirectoryPath);
                return;
            }

            File resultFolder = new File(rootDirectoryPath + File.separator + "results");
            if (!resultFolder.exists()) {
                if (!resultFolder.mkdirs()) {
                    log.severe("Failed to create directory: " + resultFolder.getAbsolutePath());
                    return;
                }
            }

            Pattern studentFolderPattern = Pattern.compile("^(f[0-9]+_)([a-zA-Z0-9]+)-mongodb-[0-9]+-[0-9]+");

            for (File folder : subfolders) {
                Matcher matcher = studentFolderPattern.matcher(folder.getName());
                if (matcher.matches()) {
                    List<String> fileLines = new ArrayList<>();
                    String studentName = matcher.group(2);
                    for (File script: Objects.requireNonNull(folder.listFiles((dir, name) -> name.endsWith(".js")))) {
                        fileLines.addAll(Files.readAllLines(script.toPath()));
                    }
                    List<Query> queryList = ScriptParser.parse(fileLines);
                    List<String> feedbackList = new CriteriaChecker().checkQueries(queryList);
                    File result = new File(resultFolder, studentName + ".csv");
                    result.createNewFile();
                    try (FileWriter writer = new FileWriter(result)) {
                        writer.write(String.join("\n", feedbackList));
                    } catch (IOException e) {
                        e.printStackTrace(); // Handle exceptions here
                    }
                }
            }

//            List<String> fileLines = Files.readAllLines(Paths.get(path + ".js"));
//            List<Query> queryList = ScriptParser.parse(fileLines);
//
//            List<String> feedbackList = new CriteriaChecker().checkQueries(queryList);
//
//            System.out.println(feedbackList);
//            new BufferedWriter(new FileWriter(path + ".csv"))
//                    .write(String.join("", feedbackList));
        } catch (IOException e) {
            //todo work out exception logic
            throw new RuntimeException(e);
        }
    }
}
