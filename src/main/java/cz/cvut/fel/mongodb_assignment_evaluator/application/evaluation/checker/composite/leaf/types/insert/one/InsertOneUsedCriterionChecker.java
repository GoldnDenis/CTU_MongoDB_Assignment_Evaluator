package cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.composite.leaf.types.insert.one;

import cz.cvut.fel.mongodb_assignment_evaluator.enums.Criteria;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.InsertedDocumentStorage;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.composite.leaf.CheckerLeaf;
import cz.cvut.fel.mongodb_assignment_evaluator.application.model.query.type.InsertQuery;

public class InsertOneUsedCriterionChecker extends CheckerLeaf<InsertQuery> {
    public InsertOneUsedCriterionChecker(InsertedDocumentStorage documentStorage) {
        super(Criteria.INSERT_ONE_USED, InsertQuery.class, documentStorage);
    }

    @Override
    public void concreteCheck(InsertQuery query) {
        if (query.isInsertOne() &&
            !query.getNonTrivialInsertedDocuments().isEmpty()) {
            currentScore++;
        }
    }
}
