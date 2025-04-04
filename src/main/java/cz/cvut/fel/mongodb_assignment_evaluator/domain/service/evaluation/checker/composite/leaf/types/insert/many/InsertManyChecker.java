package cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.checker.composite.leaf.types.insert.many;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.enums.Criteria;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type.InsertQuery;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.checker.InsertedDocumentStorage;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.checker.composite.leaf.CheckerLeaf;

public class InsertManyChecker extends CheckerLeaf<InsertQuery> {
    public InsertManyChecker(InsertedDocumentStorage documentStorage) {
        super(Criteria.INSERT_MANY_USED, InsertQuery.class, documentStorage);
    }

    @Override
    public void concreteCheck(InsertQuery query) {
        if (query.isInsertMany() &&
                !query.getNonTrivialInsertedDocuments().isEmpty()) {
            currentScore++;
        }
    }
}
