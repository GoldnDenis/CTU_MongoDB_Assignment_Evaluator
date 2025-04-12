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
import java.util.ArrayList;
import java.util.List;

@Log
@Component
@RequiredArgsConstructor
public class AssignmentEvaluator {
    private static final Logger logger = LoggerSingleton.getInstance(AssignmentEvaluator.class);
    private final DirectoryManager directoryManager;
//    private final FinalEvaluationResult finalEvaluationResult;
    private final StudentAssignmentEvaluator studentAssignmentEvaluator;

    public void evaluate(String rootDirectoryPath) {
        try {
            directoryManager.openRootDirectory(rootDirectoryPath);
            directoryManager.createResultFolder();
            List<StudentSubmission> studentSubmissions = directoryManager.getStudentSumbissions();
            studentSubmissions.forEach(this::processStudentFolder);
            // todo potential problem with the error
//            directoryManager.createFinalTable(finalEvaluationResult);
        } catch (Exception e) {
            logger.error(e.getMessage());
            log.severe("Evaluation failed: " + e.getMessage());
        }
    }

    private void processStudentFolder(StudentSubmission submission) {
        String studentName = submission.getUsername();
        log.info("---------| Started evaluation of '" + studentName + "' |---------");
        try {
            String debugStudent = "jagosmar";
            if (studentName.equals(debugStudent)) {
                System.out.println(debugStudent);
            }
            submission.addScriptLines(directoryManager.readAllJSLines(submission.getFolder()));
            studentAssignmentEvaluator.evaluateStudent(submission);
            System.out.println("ds");
//            directoryManager.createStudentEvaluationOutput(studentName, evaluationResult, StudentAssignmentEvaluator.getErrorCollector());
        } catch (Exception e) {
            logger.error(e.getMessage());
            log.severe("Evaluation of student " + studentName + " failed: " + e.getMessage());
        }
    }
}

// bendasta (245r136c), herczmax (66r1c), houskond(268r76c), jagosmar(52r5c), kankaluk(245r136c), kaufmlu1(842r5c), kunstja2(231r42c),
// kvardro2(52r1c), lokajva1(92r5c), losinmar(0r17c, 41r1c), pejsomic(229r44c), rakusdan
