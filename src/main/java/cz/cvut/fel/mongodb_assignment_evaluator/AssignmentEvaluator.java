package cz.cvut.fel.mongodb_assignment_evaluator;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.StudentSubmission;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.StudentAssignmentEvaluator;
import cz.cvut.fel.mongodb_assignment_evaluator.infrastructure.config.LoggerSingleton;
import cz.cvut.fel.mongodb_assignment_evaluator.presentation.io.DirectoryManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.util.List;

@Log
@Component
@RequiredArgsConstructor
public class AssignmentEvaluator {
    private static final Logger logger = LoggerSingleton.getInstance(AssignmentEvaluator.class);
    private final DirectoryManager directoryManager;
    private final StudentAssignmentEvaluator studentAssignmentEvaluator;

    public void evaluate(String rootDirectoryPath) {
        try {
            directoryManager.openRootDirectory(rootDirectoryPath);
            directoryManager.createResultFolder();
            List<StudentSubmission> studentSubmissions = directoryManager.getStudentSubmissions();
            studentSubmissions.forEach(this::processStudentFolder);
            // todo potential problem with the error
            directoryManager.createFinalTable(studentSubmissions, studentAssignmentEvaluator.getLoadedCriteriaNames());
        } catch (Exception e) {
            logger.error(e.getMessage());
            log.severe("Evaluation failed: " + e.getMessage());
        }
    }

    private void processStudentFolder(StudentSubmission submission) {
        String studentName = submission.getUsername();
        log.info("---------| Started evaluation of '" + studentName + "' |---------");
        try {
            submission.addScriptLines(directoryManager.readAllJSLines(submission.getFolder()));
            studentAssignmentEvaluator.evaluateStudent(submission);
            directoryManager.createStudentEvaluationFiles(submission);
        } catch (Exception e) {
            logger.error(e.getMessage());
            log.severe("Evaluation of student " + studentName + " failed: " + e.getMessage());
        }
    }
}
