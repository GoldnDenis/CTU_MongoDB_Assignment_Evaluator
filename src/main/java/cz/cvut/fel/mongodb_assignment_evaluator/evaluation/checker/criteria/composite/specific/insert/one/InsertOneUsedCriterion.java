package cz.cvut.fel.mongodb_assignment_evaluator.evaluation.checker.criteria.composite.specific.insert.one;

import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.model.enums.Criteria;
import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.checker.InsertedDocumentStorage;
import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.checker.criteria.AssignmentCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.model.query.type.InsertQuery;

public class InsertOneUsedCriterion extends AssignmentCriterion<InsertQuery> {
    public InsertOneUsedCriterion(InsertedDocumentStorage documentStorage) {
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
