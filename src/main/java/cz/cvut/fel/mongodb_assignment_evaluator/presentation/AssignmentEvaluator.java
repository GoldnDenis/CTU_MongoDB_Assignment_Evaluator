package cz.cvut.fel.mongodb_assignment_evaluator.presentation;

import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.StudentEvaluator;
import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.model.enums.FileFormats;
import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.model.result.FinalEvaluationResult;
import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.model.result.StudentEvaluationResult;
import cz.cvut.fel.mongodb_assignment_evaluator.persistence.DirectoryManager;
import lombok.extern.java.Log;

import java.io.File;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Log
public class AssignmentEvaluator {
    private static final Pattern STUDENT_FOLDER_PATTERN = Pattern.compile("^(f[0-9]+_)([a-zA-Z0-9]+)-mongodb-[0-9]+-[0-9]+");
    private static final FinalEvaluationResult finalEvaluationResult = new FinalEvaluationResult();

    public static void evaluate(String rootDirectoryPath) {
        try {
//            DirectoryManager directoryManager = new DirectoryManager(rootDirectoryPath);
            File directory = DirectoryManager.getValidatedRootDirectory(rootDirectoryPath);
            File resultFolder = DirectoryManager.createFolder(directory, "results");

            log.info("Starting evaluation...");
            for (File subFolder : DirectoryManager.getDirectorySubfolders(directory)) {
                Matcher matcher = STUDENT_FOLDER_PATTERN.matcher(subFolder.getName());
                if (matcher.matches()) {
                    String studentName = matcher.group(2);
                    File studentFolder = DirectoryManager.createFolder(resultFolder, studentName);

                    log.info("Reading the script of " + studentName + "...");
                    List<String> fileLines = DirectoryManager.readJavaScriptFiles(subFolder);

                    StudentEvaluator studentEvaluator = new StudentEvaluator(studentName);
                    StudentEvaluationResult studentResult = studentEvaluator.evaluateScriptLines(fileLines);
                    finalEvaluationResult.addStudentResult(studentName, studentResult);

                    String studentResultReport = OutputFormatter.formatToString(studentResult);
                    DirectoryManager.writeFile(studentFolder, studentName, FileFormats.CSV, studentResultReport);
                    String studentLogOutput = OutputFormatter.formatToString(StudentEvaluator.getStudentLogCollector());
                    DirectoryManager.writeFile(studentFolder, studentName, FileFormats.LOG, studentLogOutput);
                }
            }

            log.info("Generating an overall result table...");
            String finalResultTable = OutputFormatter.formatToString(finalEvaluationResult);
            DirectoryManager.writeFile(resultFolder, "final_table", FileFormats.CSV, finalResultTable);

            log.info("Evaluation has finished.");
        } catch (Exception e) {
            log.severe(e.getMessage());
        }
    }
}
