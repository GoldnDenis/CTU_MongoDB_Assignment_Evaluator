package cz.cvut.fel.mongodb_assignment_evaluator.presentation.io;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class FileReaderUtil {
    public static List<String> readAllLines(File file) throws IOException {
        return Files.readAllLines(file.toPath());
    }

    public static String readContent(File file) throws IOException {
        return Files.readString(file.toPath());
    }
}
