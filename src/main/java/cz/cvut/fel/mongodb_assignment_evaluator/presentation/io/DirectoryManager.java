package cz.cvut.fel.mongodb_assignment_evaluator.presentation.io;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.enums.FileFormats;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.StudentSubmission;
import cz.cvut.fel.mongodb_assignment_evaluator.presentation.format.OutputFormatter;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Log
@Component
@RequiredArgsConstructor
public class DirectoryManager {
    public static final Pattern STUDENT_FOLDER_PATTERN = Pattern.compile("^(f[0-9]+)_([a-zA-Z0-9]+)-mongodb-([0-9]+)-([0-9]+)");

    private final OutputFormatter outputFormatter;
    private File rootDirectory;
    private File resultFolder;

    public void openRootDirectory(String rootDirectoryPath) throws IOException {
        this.rootDirectory = DirectoryUtils.getDirectory(rootDirectoryPath);
    }

    public void createResultFolder() throws IOException {
        resultFolder = DirectoryUtils.createFolder(rootDirectory, "results");
    }

    public void createStudentEvaluationFiles(StudentSubmission submission) throws IOException {
        String studentName = submission.getUsername();
        File studentResultFolder = DirectoryUtils.createFolder(resultFolder, studentName);
        String resultTable = outputFormatter.generateTable(submission.getGradedCriteria());
        FileWriterUtil.writeFile(studentResultFolder, studentName, FileFormats.CSV, resultTable);
        String logFileContents = outputFormatter.formatLogs(submission.getLogList());
        FileWriterUtil.writeFile(studentResultFolder, studentName, FileFormats.LOG, logFileContents);
    }

    public void createFinalTable(List<StudentSubmission> studentSubmissions, List<String> criteriaNames) throws IOException {
        String finalResultTableContents = outputFormatter.generateCrossTab(studentSubmissions, criteriaNames);
        FileWriterUtil.writeFile(resultFolder, "final_table", FileFormats.CSV, finalResultTableContents);
    }

    public List<StudentSubmission> getStudentSubmissions() throws IOException {
        List<StudentSubmission> studentSubmissionList = new ArrayList<>();
        for (File folder : DirectoryUtils.getFiles(rootDirectory)) {
            if (!folder.isDirectory()) {
                continue;
            }
            Matcher matcher = DirectoryManager.STUDENT_FOLDER_PATTERN.matcher(folder.getName());
            if (matcher.matches()) {
                String year = matcher.group(1);
                String studentName = matcher.group(2);
                String date = matcher.group(3);
                String time = matcher.group(4);
                studentSubmissionList.add(new StudentSubmission(year, studentName, date, time, folder));
            }
        }
        return studentSubmissionList;
    }

    public List<String> readAllJSLines(File folder) throws IOException {
        File[] jsFiles = DirectoryUtils.getFilesOfFormat(folder, FileFormats.JS);
        List<String> fileLines = new ArrayList<>();
        List<String> createCollectionFileLines = new ArrayList<>();
        List<String> insertFileLines = new ArrayList<>();
        for (File file : jsFiles) {
            try {
                String content = FileReaderUtil.readContent(file);
                List<String> lines = Arrays.stream(content.split("\n")).toList();
                if (content.contains(".createCollection")) {
                    createCollectionFileLines.addAll(lines);
                } else if (content.contains(".insert")) {
                    insertFileLines.addAll(lines);
                } else {
                    fileLines.addAll(lines);
                }
            } catch (IOException e) {
                throw new IOException(e.getMessage());
            }
        }
        List<String> prioritySortedLines = new ArrayList<>();
        prioritySortedLines.addAll(createCollectionFileLines);
        prioritySortedLines.addAll(insertFileLines);
        prioritySortedLines.addAll(fileLines);
        return prioritySortedLines;
    }
}
