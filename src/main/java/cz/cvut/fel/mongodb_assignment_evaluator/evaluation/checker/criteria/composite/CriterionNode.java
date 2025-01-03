package cz.cvut.fel.mongodb_assignment_evaluator.evaluation.checker.criteria.composite;

import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.checker.criteria.CriterionEvaluationResult;
import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.model.query.type.Query;

import java.util.List;

public interface CriterionNode {
    void check(Query query);
    List<CriterionEvaluationResult> evaluate();
}
