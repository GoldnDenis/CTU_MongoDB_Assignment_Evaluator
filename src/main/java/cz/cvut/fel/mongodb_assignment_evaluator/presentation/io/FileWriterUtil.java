package cz.cvut.fel.mongodb_assignment_evaluator.presentation.io;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.enums.FileFormats;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileWriterUtil {
    public static void writeFile(File destFolder, String fileName, FileFormats format, String contents) throws IOException {
        File file = new File(destFolder, format.getFileNameWithFormat(fileName));
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(contents);
        } catch (IOException e) {
            throw new IOException(e.getMessage());
        }
    }
}
