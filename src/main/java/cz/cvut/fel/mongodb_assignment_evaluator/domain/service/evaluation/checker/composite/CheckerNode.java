package cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.checker.composite;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type.Query;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.result.CriterionEvaluationResult;

import java.util.List;

public interface CheckerNode {
    void check(Query query);
    List<CriterionEvaluationResult> evaluate();
}
