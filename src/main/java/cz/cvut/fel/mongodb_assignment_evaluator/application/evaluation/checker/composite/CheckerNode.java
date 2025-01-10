package cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.composite;

import cz.cvut.fel.mongodb_assignment_evaluator.application.model.result.CriterionEvaluationResult;
import cz.cvut.fel.mongodb_assignment_evaluator.application.model.query.type.Query;

import java.util.List;

public interface CheckerNode {
    void check(Query query);
    List<CriterionEvaluationResult> evaluate();
}
