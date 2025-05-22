package cz.cvut.fel.mongodb_assignment_evaluator.application;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.executor.MongoCommandExecutor;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.grader.CriteriaGrader;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.parser.ScriptParser;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.exceptions.CriteriaNotLoaded;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.StudentSubmission;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.entity.Criterion;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;

/**
 * An orchestrator class that controls the process of individual student evaluation
 */
@Log
@Component
@RequiredArgsConstructor
public class StudentEvaluator {
    private final ScriptParser scriptParser;
    private final CriteriaGrader criteriaGrader;
    private final MongoCommandExecutor scriptRunner;

    public void initEvaluator() throws CriteriaNotLoaded, IOException, InterruptedException {
        criteriaGrader.initCriteria();
        log.info("---------| Criteria were successfully loaded from the database |---------");

        scriptRunner.startMongoShell();
    }

    public void evaluateStudent(StudentSubmission studentSubmission, String script) {
        studentSubmission.addLog(Level.INFO, "-----| Started parsing student's script");
        studentSubmission.setExtractedQueries(scriptParser.parse(studentSubmission, script));
        studentSubmission.addLog(Level.INFO, "The script has been successfully parsed, extracted " + studentSubmission.getExtractedQueries().size() + " queries");

        studentSubmission.addLog(Level.INFO, "-----| Started checking queries against the criteria");
        studentSubmission.setGradedCriteria(criteriaGrader.evaluateQueries(studentSubmission));
        studentSubmission.addLog(Level.INFO, "Criteria were successfully graded");

        studentSubmission.addLog(Level.INFO, "-----| Started executing queries via MongoShell");
        try {
            scriptRunner.executeQueries(studentSubmission);
            studentSubmission.addLog(Level.INFO, "Queries have been successfully executed");
        } catch (Exception e) {
            studentSubmission.addLog(Level.SEVERE, e.getMessage());
        }
    }

    public void closeMongoShell() throws IOException, InterruptedException {
        scriptRunner.exitMongoShell();
    }

    public List<String> getLoadedCriteriaNames() {
        return criteriaGrader.getLoadedCriteria().stream()
                .map(Criterion::getName)
                .toList();
    }
}
