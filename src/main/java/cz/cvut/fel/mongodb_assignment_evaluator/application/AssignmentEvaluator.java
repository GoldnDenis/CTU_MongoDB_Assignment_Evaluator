package cz.cvut.fel.mongodb_assignment_evaluator.application;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.StudentSubmission;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.entity.Submission;
import cz.cvut.fel.mongodb_assignment_evaluator.infrastructure.DataManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.logging.Level;

/**
 * The main orchestrator class that controls the evaluation process of the whole application
 */
@Log
@Component
@RequiredArgsConstructor
public class AssignmentEvaluator {
    private final DataManager dataManager;
    private final StudentEvaluator studentEvaluator;

    public void evaluate(String rootDirectoryPath) {
        try {
            studentEvaluator.initEvaluator();

            dataManager.initDirectory();
            List<StudentSubmission> studentSubmissions = dataManager.retrieveAllStudentSubmissions(rootDirectoryPath);
            if (studentSubmissions.isEmpty()) {
                log.info("No submissions found in the directory: " + rootDirectoryPath);
                return;
            }

            studentSubmissions.forEach(this::processStudentFolder);

            studentEvaluator.closeMongoShell();

            dataManager.createFinalTable(studentEvaluator.getLoadedCriteriaNames());
            log.info("<---------| Evaluation has been finished |--------->");
        } catch (Exception e) {
            log.severe("<---------| Evaluation failed: " + e.getMessage() + " |--------->");
        }
    }

    private void processStudentFolder(StudentSubmission studentSubmission) {
        String studentName = studentSubmission.getStudentName();

        if (studentSubmission.isSubmissionPresent()) {
            Submission submission = studentSubmission.getSubmission();
            log.info("---------| Submission of '" + studentName + "' uploaded at " +
                    submission.getUploadDate().toString() + " has already been evaluated |---------");
            return;
        }

        studentSubmission.addLog(Level.INFO, "---------| Started evaluation of '" + studentName + "' |---------");
        try {
            studentSubmission.addLog(Level.INFO, "-----| Reading student's script");
            String readScript = dataManager.readStudentScripts(studentSubmission);

            studentEvaluator.evaluateStudent(studentSubmission, readScript);

            dataManager.createStudentResultFiles(studentSubmission);
            studentSubmission.addLog(Level.INFO, "---------| Script of " + studentName + " has been evaluated |---------");
        } catch (Exception e) {
            log.severe("---------| Evaluation of student " + studentName + " failed: " + e.getMessage() + " |---------");
        }
    }
}
