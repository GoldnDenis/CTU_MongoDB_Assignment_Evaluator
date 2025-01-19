package cz.cvut.fel.mongodb_assignment_evaluator.presentation.io;

import cz.cvut.fel.mongodb_assignment_evaluator.application.model.error.StudentErrorCollector;
import cz.cvut.fel.mongodb_assignment_evaluator.application.model.result.FinalEvaluationResult;
import cz.cvut.fel.mongodb_assignment_evaluator.application.model.result.StudentEvaluationResult;
import cz.cvut.fel.mongodb_assignment_evaluator.enums.FileFormats;
import cz.cvut.fel.mongodb_assignment_evaluator.presentation.format.OutputFormatter;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Log
@Component
@RequiredArgsConstructor
public class DirectoryManager {
    public static final Pattern STUDENT_FOLDER_PATTERN = Pattern.compile("^(f[0-9]+_)([a-zA-Z0-9]+)-mongodb-[0-9]+-[0-9]+");

    private final OutputFormatter outputFormatter;
    private File rootDirectory;
    private File resultFolder;

    public void openRootDirectory(String rootDirectoryPath) throws IOException {
        this.rootDirectory = DirectoryUtils.getDirectory(rootDirectoryPath);
    }

    public void createResultFolder() throws IOException {
        resultFolder = DirectoryUtils.createFolder(rootDirectory, "results");
    }

    public void createStudentEvaluationOutput(String studentName, StudentEvaluationResult evaluationResult, StudentErrorCollector studentErrorCollector) throws IOException {
        File studentResultFolder = DirectoryUtils.createFolder(resultFolder, studentName);
        String resultContents = outputFormatter.formatToString(evaluationResult);
        FileWriterUtil.writeFile(studentResultFolder, studentName, FileFormats.CSV, resultContents);
        String errorLogContents = outputFormatter.formatToString(studentErrorCollector);
        FileWriterUtil.writeFile(studentResultFolder, studentName, FileFormats.LOG, errorLogContents);
    }

    public void createFinalTable(FinalEvaluationResult evaluationResult) throws IOException {
        String finalResultTableContents = outputFormatter.formatToString(evaluationResult);
        FileWriterUtil.writeFile(resultFolder, "final_table", FileFormats.CSV, finalResultTableContents);
    }

    public Map<String, File> getStudentFolders() throws IOException {
        Map<String, File> studentFolders = new LinkedHashMap<>();
        List<File> subfolders = Arrays.stream(DirectoryUtils.getFiles(rootDirectory))
                .filter(File::isDirectory)
                .toList();
        for (File folder : subfolders) {
            Matcher matcher = DirectoryManager.STUDENT_FOLDER_PATTERN.matcher(folder.getName());
            if (matcher.matches()) {
                String studentName = matcher.group(2);
                studentFolders.put(studentName, folder);
            }
        }
        return studentFolders;
    }

    public List<String> readAllJSFilesAllLines(File folder) throws IOException {
        File[] jsFiles = DirectoryUtils.getFilesOfFormat(folder, FileFormats.JS);
        List<String> lines = new ArrayList<>();
        for (File file : jsFiles) {
            try {
                lines.addAll(FileReaderUtil.readAllLines(file));
            } catch (IOException e) {
                throw new IOException(e.getMessage());
            }
        }
        return lines;
    }
}
