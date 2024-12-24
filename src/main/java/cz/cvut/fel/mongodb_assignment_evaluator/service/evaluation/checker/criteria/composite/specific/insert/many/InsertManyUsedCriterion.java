package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.composite.specific.insert.many;

import cz.cvut.fel.mongodb_assignment_evaluator.service.enums.Criteria;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.InsertedDocumentStorage;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.AssignmentCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.query.type.InsertQuery;

public class InsertManyUsedCriterion extends AssignmentCriterion<InsertQuery> {
    public InsertManyUsedCriterion(InsertedDocumentStorage documentStorage) {
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
