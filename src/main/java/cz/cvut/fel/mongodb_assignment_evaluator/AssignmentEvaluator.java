package cz.cvut.fel.mongodb_assignment_evaluator;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.StudentSubmission;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.result.FinalEvaluationResult;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.result.GradedSubmission;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.StudentAssignmentEvaluator;
import cz.cvut.fel.mongodb_assignment_evaluator.infrastructure.config.LoggerSingleton;
import cz.cvut.fel.mongodb_assignment_evaluator.presentation.io.DirectoryManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Log
@Component
@RequiredArgsConstructor
public class AssignmentEvaluator {
    private static final Logger logger = LoggerSingleton.getInstance(AssignmentEvaluator.class);
    private final DirectoryManager directoryManager;
    private final FinalEvaluationResult finalEvaluationResult;
    private final StudentAssignmentEvaluator studentAssignmentEvaluator;

    public void evaluate(String rootDirectoryPath) {
        try {
            directoryManager.openRootDirectory(rootDirectoryPath);
            directoryManager.createResultFolder();
            directoryManager.getStudentWorks().forEach(this::processStudentFolder);
//            directoryManager.createFinalTable(finalEvaluationResult);
        } catch (Exception e) {
            logger.error(e.getMessage());
            log.severe("Evaluation failed: " + e.getMessage());
        }
    }

    private void processStudentFolder(StudentSubmission work) {
        String studentName = work.getUsername();
        log.info("---------| Started evaluation of '" + studentName + "' |---------");
        try {
            List<String> scriptLines = directoryManager.readAllJSFilesAllLines(work.getFolder());
//            log.info("Successfully read the script of '" + studentName + "'");
            GradedSubmission evaluationResult = studentAssignmentEvaluator.evaluateScript(scriptLines);
            finalEvaluationResult.addStudentResult(studentName, evaluationResult);
            directoryManager.createStudentEvaluationOutput(studentName, evaluationResult, StudentAssignmentEvaluator.getErrorCollector());
        } catch (IOException e) {
            logger.error(e.getMessage());
            log.severe("Evaluation of student " + studentName + " failed: " + e.getMessage());
        }
    }
}

