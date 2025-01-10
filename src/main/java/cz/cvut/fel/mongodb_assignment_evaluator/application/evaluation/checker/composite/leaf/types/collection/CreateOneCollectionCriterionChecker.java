package cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.composite.leaf.types.collection;

import cz.cvut.fel.mongodb_assignment_evaluator.enums.Criteria;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.InsertedDocumentStorage;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.composite.leaf.CheckerLeaf;
import cz.cvut.fel.mongodb_assignment_evaluator.application.model.query.type.CreateCollectionQuery;

public class CreateOneCollectionCriterionChecker extends CheckerLeaf<CreateCollectionQuery> {
    public CreateOneCollectionCriterionChecker(InsertedDocumentStorage documentStorage) {
        super(Criteria.CREATE_ONE_COLLECTION, CreateCollectionQuery.class, documentStorage);
    }

    @Override
    protected void concreteCheck(CreateCollectionQuery query) {
        String collectionName = query.getCollectionName();
        if (documentStorage.createCollection(collectionName)) {
            currentScore++;
        }
    }
}
