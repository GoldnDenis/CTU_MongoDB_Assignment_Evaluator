package cz.cvut.fel.mongodb_assignment_evaluator.presentation;

import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.checker.criteria.CriterionEvaluationResult;
import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.model.result.FinalEvaluationResult;
import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.model.result.StudentEvaluationResult;
import lombok.extern.java.Log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.util.regex.Pattern;

@Log
public class DirectoryManager {
    private final File directory;
    private final File resultFolder;

    public DirectoryManager(String directoryPath) {
        directory = getValidatedRootDirectory(directoryPath);
        resultFolder = createResultsFolder();
    }

    public File[] getDirectorySubfolders() {
        File[] subfolders = directory.listFiles();
        if (subfolders == null) {
            throw new RuntimeException("No subfolders found in directory: " + directory.getAbsolutePath());
        }
        return subfolders;
    }

    private File getValidatedRootDirectory(String rootDirectoryPath) {
        File directory = new File(rootDirectoryPath);
        if (!directory.isDirectory()) {
            throw new RuntimeException("Path '" + directory.getAbsolutePath() + "' is not a directory");
        }
        return directory;
    }

    private File createResultsFolder() {
        File resultFolder = new File(directory, "results");
        if (!resultFolder.exists() && !resultFolder.mkdirs()) {
            throw new RuntimeException("Failed to create folder for results at '" + resultFolder.getAbsolutePath() + "'");
        }
        return resultFolder;
    }

    public static List<String> readJavaScriptFiles(File folder) {
        List<String> fileLines = new ArrayList<>();
        File[] scripts = folder.listFiles((dir, name) -> name.endsWith(".js"));
        if (scripts != null) {
            for (File script : scripts) {
                try {
                    fileLines.addAll(Files.readAllLines(script.toPath()));
                } catch (IOException e) {
                    log.severe("Error reading file: " + script.getName());
                }
            }
        }
        return fileLines;
    }

    public void writeCSV(String name, String contents) {
        name = name + ".csv";
        File file = new File(resultFolder, name);
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(contents);
        } catch (IOException e) {
            log.severe("Error while writing to " + name);
        }
    }
}
