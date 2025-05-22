package cz.cvut.fel.mongodb_assignment_evaluator.infrastructure;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.enums.FileFormats;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.enums.RegularExpressions;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.exceptions.ScriptNotRead;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.exceptions.SourceDirectoryNotFound;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.exceptions.StudentFolderNotFound;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.StudentSubmission;
import cz.cvut.fel.mongodb_assignment_evaluator.infrastructure.io.DirectoryUtils;
import cz.cvut.fel.mongodb_assignment_evaluator.infrastructure.io.FileReaderUtil;
import cz.cvut.fel.mongodb_assignment_evaluator.infrastructure.io.FileWriterUtil;
import cz.cvut.fel.mongodb_assignment_evaluator.infrastructure.persistence.sql.projection.SubmissionResultView;
import cz.cvut.fel.mongodb_assignment_evaluator.infrastructure.persistence.sql.service.SubmissionService;
import cz.cvut.fel.mongodb_assignment_evaluator.observability.format.OutputFormatter;
import cz.cvut.fel.mongodb_assignment_evaluator.observability.merger.ContentMerger;
import lombok.extern.java.Log;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Handles all interactions with external data sources, i.e. IO and SQL database
 */
@Log
@Component
public class DataManager {
    private final OutputFormatter outputFormatter;
    private final SubmissionService submissionService;

    private final Map<String, File> studentSourceFolders;
    private final Map<String, File> studentResultFolders;
    private File resultFolder;

    public DataManager(OutputFormatter outputFormatter, SubmissionService submissionService) {
        this.outputFormatter = outputFormatter;
        this.submissionService = submissionService;
        this.studentSourceFolders = new HashMap<>();
        this.studentResultFolders = new HashMap<>();
    }

    /**
     * Create folders for results and logs
     * @throws IOException
     */
    public void initDirectory() throws IOException {
        resultFolder = DirectoryUtils.createFolder("results");
        DirectoryUtils.createFolder("logs");
    }

    /**
     * Navigate to the directory using provided path and look for all folders, locate and retrieve all student submissions
     * The folder name has to match the pattern "f<semester>_<username>-mongodb-<YYYYMMDD>-<HHMMss>.
     * Uses service to generate a StudentSubmission object that is linked to the database via Submission
     * @param sourceFolderPath
     * @return all recognised by folder submissions
     * @throws IOException
     */
    public List<StudentSubmission> retrieveAllStudentSubmissions(String sourceFolderPath) throws IOException {
        Pattern studentFolderPattern = Pattern.compile(RegularExpressions.STUDENT_FOLDER.getRegex());
        List<StudentSubmission> studentSubmissions = new ArrayList<>();
        File[] sourceFolders;
        try {
            sourceFolders = DirectoryUtils.getFiles(DirectoryUtils.getDirectory(sourceFolderPath));
        } catch (IOException e) {
            throw new SourceDirectoryNotFound(sourceFolderPath);
        }
        for (File folder : sourceFolders) {
            if (!folder.isDirectory()) {
                continue;
            }
            Matcher matcher = studentFolderPattern.matcher(folder.getName());
            if (matcher.matches()) {
                String course = matcher.group(1);
                String studentName = matcher.group(2);
                String date = matcher.group(3);
                String time = matcher.group(4);
                int studyYear = Integer.parseInt(date.substring(0, 4));

                studentSourceFolders.put(studentName, folder);

                studentSubmissions.add(
                        submissionService.getStudentSubmission(studentName, studyYear, date + time)
                );
            }
        }
        return studentSubmissions;
    }

    /**
     * Reads all Javascript files from the folder that matches submission.
     * In case, if present more than one, it consolidates them into a single file using the special merging by priority.
     * @param submission
     * @return a full script with student's queries
     * @throws IOException
     * @throws ScriptNotRead was not able to read a single javascript file
     */
    public String readStudentScripts(StudentSubmission submission) throws IOException, ScriptNotRead {
        String studentName = submission.getStudentName();
        if (!studentSourceFolders.containsKey(studentName)) {
            throw new StudentFolderNotFound(studentName, "source");
        }
        File sourceFolder = studentSourceFolders.get(studentName);

        File[] jsFiles = DirectoryUtils.getFilesOfFormat(sourceFolder, FileFormats.JS);
        List<String> contentsList = new LinkedList<>();
        for (File file : jsFiles) {
            try {
                contentsList.add(FileReaderUtil.readContent(file));
            } catch (IOException e) {
                submission.addLog(Level.SEVERE, "Was not able to read the file at '" + file.getAbsolutePath() + "'");
            }
        }
        if (contentsList.isEmpty()) {
            throw new ScriptNotRead();
        }

        submission.addLog(Level.INFO, "Read " + contentsList.size() + " scripts in total");

        // merging
        String mergedScript = ContentMerger.mergeScriptContentsByPriority(contentsList);

        File studentResultFolder = DirectoryUtils.createFolder(resultFolder, studentName);
        FileWriterUtil.createFile(studentResultFolder,
                "consolidated_script", FileFormats.JS,
                mergedScript
        );
        studentResultFolders.put(studentName, studentResultFolder);

        return mergedScript;
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
                outputFormatter.generateExecutionLog(submission.getExtractedQueries())
        );
        FileWriterUtil.createFile(
                studentResultFolder, studentName + "_feedback", FileFormats.LOG,
                outputFormatter.generateStudentFeedback(submission)
        );
        submissionService.saveSubmission(submission);
    }

    public void createFinalTable(List<String> criteriaNames) throws IOException {
        List<SubmissionResultView> foundResults = submissionService.getLatestStudentResults();
        if (foundResults.isEmpty()) {
            log.warning("No results were found to generate the table");
        }
        String finalResultTableContents = outputFormatter.generateCrossTab(foundResults, criteriaNames);
        FileWriterUtil.createFile(resultFolder, "final_table", FileFormats.CSV, finalResultTableContents);
    }
}
