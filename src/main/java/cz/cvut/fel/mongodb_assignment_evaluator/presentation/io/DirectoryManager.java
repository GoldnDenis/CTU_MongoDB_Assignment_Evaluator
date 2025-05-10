package cz.cvut.fel.mongodb_assignment_evaluator.presentation.io;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.enums.FileFormats;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.exceptions.StudentFolderNotFound;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.StudentSubmission;
import cz.cvut.fel.mongodb_assignment_evaluator.persistence.service.SubmissionService;
import cz.cvut.fel.mongodb_assignment_evaluator.presentation.format.OutputFormatter;
import lombok.extern.java.Log;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Log
@Component
public class DirectoryManager {
    public static final Pattern STUDENT_FOLDER_PATTERN = Pattern.compile("^(f[0-9]+)_([a-zA-Z0-9]+)-mongodb-([0-9]+)-([0-9]+)");

    private final OutputFormatter outputFormatter;
    private final SubmissionService submissionService;

    private final Map<String, File> studentSourceFolders;
    private final Map<String, File> studentResultFolders;
    private File resultFolder;

    public DirectoryManager(OutputFormatter outputFormatter, SubmissionService submissionService) {
        this.outputFormatter = outputFormatter;
        this.submissionService = submissionService;
        this.studentSourceFolders = new HashMap<>();
        this.studentResultFolders = new HashMap<>();
    }

    public void initDirectory() throws IOException {
        resultFolder = DirectoryUtils.createFolder("results");
        DirectoryUtils.createFolder("logs");
    }

    public List<StudentSubmission> readStudentSubmissions(String sourceFolder) throws IOException {
        List<StudentSubmission> studentSubmissions = new ArrayList<>();
        for (File folder : DirectoryUtils.getFiles(DirectoryUtils.getDirectory(sourceFolder))) {
            if (!folder.isDirectory()) {
                continue;
            }
            Matcher matcher = DirectoryManager.STUDENT_FOLDER_PATTERN.matcher(folder.getName());
            if (matcher.matches()) {
                String year = matcher.group(1);
                String studentName = matcher.group(2);
                String date = matcher.group(3);
                String time = matcher.group(4);
                int studyYear = Integer.parseInt(date.substring(0, 4));

                studentSourceFolders.put(studentName, folder);
                File studentResultFolder = DirectoryUtils.createFolder(resultFolder, studentName);
                studentResultFolders.put(studentName, studentResultFolder);

                studentSubmissions.add(
                        submissionService.getStudentSubmission(studentName, studyYear, date + time)
                );
            }
        }
        return studentSubmissions;
    }

    public List<String> readStudentScripts(String studentName) throws IOException {
        if (!studentSourceFolders.containsKey(studentName)) {
            throw new StudentFolderNotFound(studentName, "source");
        }
        File sourceFolder = studentSourceFolders.get(studentName);
        File[] jsFiles = DirectoryUtils.getFilesOfFormat(sourceFolder, FileFormats.JS);

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

        File studentResultFolder = studentResultFolders.get(studentName);
        FileWriterUtil.createFile(studentResultFolder,
                "consolidated_script", FileFormats.JS,
                prioritySortedLines
        );

        return prioritySortedLines;
    }

    public void createStudentResultFiles(StudentSubmission submission) throws IOException {
        String studentName = submission.getStudentName();
        if (!studentResultFolders.containsKey(studentName)) {
            throw new StudentFolderNotFound(studentName, "result");
        }
        File studentResultFolder = studentResultFolders.get(studentName);
        FileWriterUtil.createFile(
                studentResultFolder, studentName, FileFormats.CSV,
                outputFormatter.generateTable(submission.getGradedCriteria())
        );
        FileWriterUtil.createFile(
                studentResultFolder, studentName + "_evaluation", FileFormats.LOG,
                outputFormatter.generateEvaluationLog(submission.getEvaluationLogList())
        );
        FileWriterUtil.createFile(
                studentResultFolder, studentName + "_execution", FileFormats.LOG,
                outputFormatter.generateExecutionLog(submission.getQueryList())
        );
        FileWriterUtil.createFile(
                studentResultFolder, studentName + "_feedback", FileFormats.LOG,
                outputFormatter.generateStudentFeedback(submission)
        );
        submissionService.saveSubmission(submission);
    }

    public void createFinalTable(List<StudentSubmission> submissions, List<String> criteriaNames) throws IOException {
        String finalResultTableContents = outputFormatter.generateCrossTab(submissions, criteriaNames);
        FileWriterUtil.createFile(resultFolder, "final_table", FileFormats.CSV, finalResultTableContents);
    }
}
