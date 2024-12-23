package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.composite.specific.collection;

import cz.cvut.fel.mongodb_assignment_evaluator.service.enums.Criteria;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.InsertedDocumentStorage;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.AssignmentCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.EvaluationResult;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.composite.CriterionNode;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.query.type.CreateCollectionQuery;

import java.util.List;

//public class CreateOneCollectionCriterion extends AssignmentCriterion implements CriterionNode<CreateCollectionQuery> {
public class CreateOneCollectionCriterion extends AssignmentCriterion<CreateCollectionQuery> {
//    private final CreateCollectionGroupCriterion parent;

//    public CreateOneCollectionCriterion(CreateCollectionGroupCriterion parent) {
//        super(Criteria.CREATE_ONE_COLLECTION);
////        this.parent = parent;
//    }

    public CreateOneCollectionCriterion(InsertedDocumentStorage documentStorage) {
        super(Criteria.CREATE_ONE_COLLECTION, CreateCollectionQuery.class, documentStorage);
//        this.parent = parent;
    }

//    @Override
//    protected void concreteCheck(Query query) {
//        String collectionName = parent.getCollectionName();
//        InsertedDocumentStorage documentStorage = parent.getDocumentStorage();
//        if (collectionName != null &&
//                documentStorage.createCollection(collectionName)) {
//            currentScore++;
//        }
//    }

    @Override
    protected void concreteCheck(CreateCollectionQuery query) {
        String collectionName = query.getCollectionName().getValue();
        if (collectionName != null &&
                documentStorage.createCollection(collectionName)) {
            currentScore++;
        }
    }
}
