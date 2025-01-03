package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation;

import lombok.extern.java.Log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

@Log
public class DirectoryManager {
    public static File getValidatedRootDirectory(String rootDirectoryPath) {
        File rootDirectory = new File(rootDirectoryPath);
        if (!rootDirectory.isDirectory()) {
            log.severe("Root directory is not a directory: " + rootDirectoryPath);
            return null;
        }
        return rootDirectory;
    }

    public static File createResultsFolder(File rootDirectory) {
        File resultFolder = new File(rootDirectory, "results");
        if (!resultFolder.exists() && !resultFolder.mkdirs()) {
            log.severe("Failed to create directory: " + resultFolder.getAbsolutePath());
            return null;
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

    public static void writeFeedback(File resultFolder, String studentName, List<String> feedbackList) {
        File resultFile = new File(resultFolder, studentName + ".csv");
        try (FileWriter writer = new FileWriter(resultFile)) {
            writer.write(String.join("\n", feedbackList));
        } catch (IOException e) {
            log.severe("Error writing feedback for " + studentName);
        }
    }
}
