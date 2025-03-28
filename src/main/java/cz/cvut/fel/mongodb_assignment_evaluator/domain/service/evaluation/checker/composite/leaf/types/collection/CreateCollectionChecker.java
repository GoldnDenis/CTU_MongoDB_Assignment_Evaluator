package cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.checker.composite.leaf.types.collection;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.enums.Criteria;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type.CreateCollectionQuery;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.checker.InsertedDocumentStorage;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.checker.composite.leaf.CheckerLeaf;

public class CreateCollectionChecker extends CheckerLeaf<CreateCollectionQuery> {
    public CreateCollectionChecker(InsertedDocumentStorage documentStorage) {
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
