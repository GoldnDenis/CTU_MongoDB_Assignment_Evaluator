package cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.composite.leaf.types.unknown;

import cz.cvut.fel.mongodb_assignment_evaluator.enums.Criteria;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.InsertedDocumentStorage;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.composite.leaf.CheckerLeaf;
import cz.cvut.fel.mongodb_assignment_evaluator.application.model.query.type.Query;

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
