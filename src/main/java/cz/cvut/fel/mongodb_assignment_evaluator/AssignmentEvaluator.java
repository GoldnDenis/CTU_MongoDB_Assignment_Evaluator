package cz.cvut.fel.mongodb_assignment_evaluator;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.StudentAssignmentEvaluator;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.StudentSubmission;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.entity.Submission;
import cz.cvut.fel.mongodb_assignment_evaluator.presentation.io.DirectoryManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.logging.Level;

@Log
@Component
@RequiredArgsConstructor
public class AssignmentEvaluator {
    private final DirectoryManager directoryManager;
    private final StudentAssignmentEvaluator studentAssignmentEvaluator;

    public void evaluate(String rootDirectoryPath) {
        try {
            directoryManager.initDirectory();

            List<StudentSubmission> studentSubmissions = directoryManager.readStudentSubmissions(rootDirectoryPath);
            if (studentSubmissions.isEmpty()) {
                log.info("No submissions found in the directory: " + rootDirectoryPath);
                return;
            }

            studentAssignmentEvaluator.initEvaluator();
            studentSubmissions.forEach(this::processStudentFolder);

            studentAssignmentEvaluator.closeMongoShell();

            directoryManager.createFinalTable(studentSubmissions, studentAssignmentEvaluator.getLoadedCriteriaNames());
            log.info("<---------| Evaluation has been finished |--------->");
        } catch (Exception e) {
            log.severe("<---------| Evaluation failed: " + e.getMessage() + " |--------->");
        }
        System.exit(0);
    }

    private void processStudentFolder(StudentSubmission studentSubmission) {
        String studentName = studentSubmission.getStudentName();

        if (studentSubmission.isSubmissionPresent()) {
            Submission submission = studentSubmission.getSubmission();
            log.info("---------| Submission of '" + studentName + "' uploaded at " + submission.getUploadDate().toString() + " has already been evaluated |---------");
            return;
        }

        studentSubmission.addLog(Level.INFO, "---------| Started evaluation of '" + studentName + "' |---------");
        try {
            studentSubmission.addLog(Level.INFO, "-----| Reading student's script");
            List<String> scriptLines = directoryManager.readStudentScripts(studentName);
            studentSubmission.addLog(Level.INFO, "Script has been read, found " + scriptLines.size() + " lines");

            studentAssignmentEvaluator.evaluateStudent(studentSubmission, scriptLines);

            directoryManager.createStudentResultFiles(studentSubmission);
            studentSubmission.addLog(Level.INFO, "---------| Script of " + studentName + " has been evaluated |---------");
        } catch (Exception e) {
            log.severe("---------| Evaluation of student " + studentName + " failed: " + e.getMessage() + " |---------");
        }
    }
}
