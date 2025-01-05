package cz.cvut.fel.mongodb_assignment_evaluator.persistence;

import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.model.enums.FileFormats;
import lombok.extern.java.Log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

@Log
public class DirectoryManager {
//    private final File directory;
//    private final File resultFolder;
//
//    public DirectoryManager(String directoryPath) {
//        directory = getValidatedRootDirectory(directoryPath);
//        resultFolder = createFolder(directory,"results");
//    }
    public static File getValidatedRootDirectory(String rootDirectoryPath) {
        File directory = new File(rootDirectoryPath);
        if (!directory.isDirectory()) {
            throw new RuntimeException("Path '" + directory.getAbsolutePath() + "' is not a directory");
        }
        return directory;
    }

    public static File[] getDirectorySubfolders(File directory) {
        File[] subfolders = directory.listFiles();
        if (subfolders == null) {
            throw new RuntimeException("No subfolders found in directory: " + directory.getAbsolutePath());
        }
        return subfolders;
    }

    public static File createFolder(File destFolder, String name) {
        File folder = new File(destFolder, name);
        if (!folder.exists() && !folder.mkdirs()) {
            throw new RuntimeException("Failed to create '" + name + "' folder at '" + folder.getAbsolutePath() + "'");
        }
        return folder;
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

    public static void writeFile(File destFolder, String name, FileFormats format, String contents) {
        name = name + format.getFileFormat();
        File file = new File(destFolder, name);
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(contents);
        } catch (IOException e) {
            log.severe("Error while writing to " + name);
        }
    }
}
