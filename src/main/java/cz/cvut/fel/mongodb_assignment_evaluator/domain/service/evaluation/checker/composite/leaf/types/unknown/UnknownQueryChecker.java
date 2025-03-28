package cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.checker.composite.leaf.types.unknown;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.enums.Criteria;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type.Query;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.checker.InsertedDocumentStorage;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.checker.composite.leaf.CheckerLeaf;

public class UnknownQueryChecker extends CheckerLeaf<Query> {
    public UnknownQueryChecker(InsertedDocumentStorage documentStorage) {
        super(Criteria.UNKNOWN_UNRECOGNISED, Query.class, documentStorage);
    }

    @Override
    protected void concreteCheck(Query query) {
        evaluationResult.setCriterionModifier(evaluationResult.getCriterionModifier() + 1);
        currentScore++;
    }
}
