package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.composite.specific.unknown;

import cz.cvut.fel.mongodb_assignment_evaluator.service.enums.Criteria;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.InsertedDocumentStorage;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.AssignmentCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.query.type.Query;

public class UnknownQueryCriterion extends AssignmentCriterion<Query> {
    public UnknownQueryCriterion(InsertedDocumentStorage documentStorage) {
        super(Criteria.UNKNOWN_UNRECOGNISED, Query.class, documentStorage);
    }

    @Override
    protected void concreteCheck(Query query) {
        criterionEvaluationResult.setCriterionModifier(criterionEvaluationResult.getCriterionModifier() + 1);
        currentScore++;
    }
}
