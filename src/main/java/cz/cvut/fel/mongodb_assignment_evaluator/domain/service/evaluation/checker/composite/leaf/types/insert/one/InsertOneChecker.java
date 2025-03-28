package cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.checker.composite.leaf.types.insert.one;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.enums.Criteria;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type.InsertQuery;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.checker.InsertedDocumentStorage;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.checker.composite.leaf.CheckerLeaf;

public class InsertOneChecker extends CheckerLeaf<InsertQuery> {
    public InsertOneChecker(InsertedDocumentStorage documentStorage) {
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
