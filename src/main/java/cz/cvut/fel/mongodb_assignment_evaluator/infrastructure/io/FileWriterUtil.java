package cz.cvut.fel.mongodb_assignment_evaluator.infrastructure.io;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.enums.FileFormats;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class FileWriterUtil {
    public static File createFile(File destFolder, String fileName, FileFormats format, String contents) throws IOException {
        File file = new File(destFolder, format.getFileNameWithFormat(fileName));
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(contents);
        } catch (IOException e) {
            throw new IOException(e.getMessage());
        }
        return file;
    }

    public static File createFile(File destFolder, String fileName, FileFormats format, List<String> contents) throws IOException {
        File file = new File(destFolder, format.getFileNameWithFormat(fileName));
        try (FileWriter writer = new FileWriter(file)) {
            for (String line : contents) {
                writer.write(line);
                if (!line.endsWith(System.lineSeparator()) && !line.endsWith("\n")) {
                    writer.write(System.lineSeparator());
                }
            }
        } catch (IOException e) {
            throw new IOException(e.getMessage());
        }
        return file;
    }
}
