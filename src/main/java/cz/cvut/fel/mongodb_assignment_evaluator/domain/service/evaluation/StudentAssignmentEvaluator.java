package cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.StudentSubmission;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.criteria.Crit;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.criteria.CriteriaEvaluator;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.parser.ScriptParser;
import lombok.extern.java.Log;
import org.springframework.stereotype.Component;

import java.util.List;

@Log
@Component
public class StudentAssignmentEvaluator {
    private ScriptParser scriptParser;
    private CriteriaEvaluator criteriaEvaluator;

    public StudentAssignmentEvaluator() {
        scriptParser = new ScriptParser();
        criteriaEvaluator = new CriteriaEvaluator();
    }

    public void evaluateStudent(StudentSubmission submission) {
        scriptParser.extractQueries(submission);
        criteriaEvaluator.evaluateQueries(submission);
        log.info("Script of " + submission.getUsername() + " has been evaluated");
    }

    public List<String> getLoadedCriteriaNames() {
        return criteriaEvaluator.getLoadedCriteria().stream()
                .map(Crit::getName)
                .toList();
    }
}
