package cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.criteria.CriteriaEvaluator;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.execution.MongoShellExecutor;
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

@Log
@Component
@RequiredArgsConstructor
public class StudentAssignmentEvaluator {
    private final ScriptParser scriptParser;
    private final CriteriaEvaluator criteriaEvaluator;
    private final MongoShellExecutor scriptRunner;

    public void initEvaluator() throws CriteriaNotLoaded, IOException {
        criteriaEvaluator.initCriteria();
        log.info("---------| Criteria were successfully loaded from the database |---------");

        scriptRunner.startMongoShell();
    }

    public void evaluateStudent(StudentSubmission studentSubmission, List<String> scriptLines) throws IOException, InterruptedException {
        studentSubmission.addLog(Level.INFO, "-----| Started parsing student's script");
        scriptParser.extractQueries(studentSubmission, scriptLines);
        studentSubmission.addLog(Level.INFO, "-----| The script has been successfully parsed, extracted " + studentSubmission.getQueryList().size() + " queries");

        studentSubmission.addLog(Level.INFO, "-----| Started checking queries against the criteria");
        criteriaEvaluator.evaluateQueries(studentSubmission);
        studentSubmission.addLog(Level.INFO, "-----| Queries have been successfully checked");

        studentSubmission.addLog(Level.INFO, "-----| Started executing queries via MongoShell");
        scriptRunner.executeQueries(studentSubmission);
        studentSubmission.addLog(Level.INFO, "-----| Queries have been successfully executed");
    }

    public void closeMongoShell() throws IOException, InterruptedException {
        scriptRunner.exitMongoShell();
    }

    public List<String> getLoadedCriteriaNames() {
        return criteriaEvaluator.getLoadedCriteria().stream()
                .map(Criterion::getName)
                .toList();
    }
}
