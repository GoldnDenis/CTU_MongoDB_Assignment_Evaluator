package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.composite;

import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.CriterionEvaluationResult;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.query.type.Query;

import java.util.List;

//public interface CriterionNode<Q extends Query> {
public interface CriterionNode {
//    void check(Query query);
    void check(Query query);
    List<CriterionEvaluationResult> evaluate();
}
