package cz.cvut.fel.mongodb_assignment_evaluator.presentation;

import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.StudentEvaluator;
import cz.cvut.fel.mongodb_assignment_evaluator.config.LoggerSingleton;
import cz.cvut.fel.mongodb_assignment_evaluator.application.model.result.FinalEvaluationResult;
import cz.cvut.fel.mongodb_assignment_evaluator.application.model.result.StudentEvaluationResult;
import cz.cvut.fel.mongodb_assignment_evaluator.presentation.io.DirectoryManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Log
@Component
@RequiredArgsConstructor
public class AssignmentEvaluator {
    private static final Logger logger = LoggerSingleton.getInstance(AssignmentEvaluator.class);
    private final DirectoryManager directoryManager;
    private final FinalEvaluationResult finalEvaluationResult;

    public void evaluate(String rootDirectoryPath) {
        try {
            directoryManager.openRootDirectory(rootDirectoryPath);
            directoryManager.createResultFolder();
            directoryManager.getStudentFolders().forEach(this::processStudentFolder);
            directoryManager.createFinalTable(finalEvaluationResult);
        } catch (Exception e) {
            logger.error(e.getMessage());
            log.severe("Evaluation failed: " + e.getMessage());
        }
    }

    private void processStudentFolder(String studentName, File studentFolder) {
        try {
            List<String> scriptLines = directoryManager.readAllJSFilesAllLines(studentFolder);
            log.info("Successfully read the script of " + studentName);

            StudentEvaluationResult evaluationResult = new StudentEvaluator(studentName).evaluateScript(scriptLines);
            finalEvaluationResult.addStudentResult(studentName, evaluationResult);

            directoryManager.createStudentEvaluationOutput(studentName, evaluationResult, StudentEvaluator.getErrorCollector());
            log.info("Successfully generated output for " + studentName);
        } catch (IOException e) {
            logger.error(e.getMessage());
            log.severe("Evaluation of student " + studentName + " failed: " + e.getMessage());
        }
    }
}

