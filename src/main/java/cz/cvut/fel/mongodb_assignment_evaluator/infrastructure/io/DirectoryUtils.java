package cz.cvut.fel.mongodb_assignment_evaluator.infrastructure.io;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.enums.FileFormats;

import java.io.File;
import java.io.IOException;

public class DirectoryUtils {
    public static File getDirectory(String rootDirectoryPath) throws IOException {
        File directory = new File(rootDirectoryPath);
        if (!directory.isDirectory()) {
            throw new IOException("Not found a directory at ''" + directory.getAbsolutePath() + "'");
        }
        return directory;
    }

    public static File createFolder(String name) throws IOException {
        File folder = new File(name);
        if (!folder.exists() && !folder.mkdirs()) {
            throw new IOException("Failed to create '" + name + "' folder at '" + folder.getAbsolutePath() + "'");
        }
        return folder;
    }

    public static File createFolder(File destFolder, String name) throws IOException {
        File folder = new File(destFolder, name);
        if (!folder.exists() && !folder.mkdirs()) {
            throw new IOException("Failed to create '" + name + "' folder at '" + folder.getAbsolutePath() + "'");
        }
        return folder;
    }

    public static File[] getFiles(File folder) throws IOException {
        File[] subFiles = folder.listFiles();
        if (subFiles == null) {
            throw new IOException("No files found in folder '" + folder.getAbsolutePath() + "'");
        }
        return subFiles;
    }

    public static File[] getFilesOfFormat(File folder, FileFormats format) throws IOException {
        File[] subFiles = folder.listFiles((dir, name) -> format.ofFormat(name));
        if (subFiles == null) {
            throw new IOException("No files of '" + format.getFileFormat() + "' found in folder '" + folder.getAbsolutePath() + "'");
        }
        return subFiles;
    }
}
