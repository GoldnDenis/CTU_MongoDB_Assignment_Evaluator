package cz.cvut.fel.mongodb_assignment_evaluator.evaluation.checker.criteria.composite.specific.collection;

import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.model.enums.Criteria;
import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.checker.InsertedDocumentStorage;
import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.checker.criteria.AssignmentCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.model.query.type.CreateCollectionQuery;

public class CreateOneCollectionCriterion extends AssignmentCriterion<CreateCollectionQuery> {
    public CreateOneCollectionCriterion(InsertedDocumentStorage documentStorage) {
        super(Criteria.CREATE_ONE_COLLECTION, CreateCollectionQuery.class, documentStorage);
    }

    @Override
    protected void concreteCheck(CreateCollectionQuery query) {
        String collectionName = query.getCollectionName().getValue();
        if (documentStorage.createCollection(collectionName)) {
            currentScore++;
        }
    }
}
