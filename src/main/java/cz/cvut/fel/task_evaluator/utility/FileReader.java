package cz.cvut.fel.task_evaluator.utility;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class FileReader {
    public static String readFile(String path) throws IOException {
        return new String(Files.readAllBytes(Paths.get(path)));
    }

    public static List<String> readAllLines(String path) throws IOException {
        return Files.readAllLines(Paths.get(path));
    }
}
