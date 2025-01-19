package cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.composite.leaf.types.insert.many;

import cz.cvut.fel.mongodb_assignment_evaluator.enums.Criteria;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.InsertedDocumentStorage;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.composite.leaf.CheckerLeaf;
import cz.cvut.fel.mongodb_assignment_evaluator.application.model.query.type.InsertQuery;

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
