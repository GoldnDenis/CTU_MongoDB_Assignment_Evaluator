package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.composite.group;

import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.InsertedDocumentStorage;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.AssignmentCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.composite.specific.collection.CreateOneCollectionCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.query.type.CreateCollectionQuery;

import java.util.List;

//@Getter
public class CreateCollectionGroupCriterion extends GroupCriterion<CreateCollectionQuery> {
//    private List<CriterionNode<CreateCollectionQuery>> children;
//    private String collectionName;
//    private BsonDocument options;

    public CreateCollectionGroupCriterion(InsertedDocumentStorage documentStorage) {
        super(documentStorage);
//        children = List.of(
//                new CreateOneCollectionCriterion(documentStorage)
//        );
    }

    @Override
    protected List<AssignmentCriterion<CreateCollectionQuery>> initCriteria() {
        return List.of(
                new CreateOneCollectionCriterion(documentStorage)
        );
    }

//    @Override
//    public void visitStringParameter(StringParameter parameter) {
//        if (currentParameterIndex == 0) {
//            collectionName = parameter.getValue();
//        }
//    }
//
//    @Override
//    public void visitDocumentParameter(DocumentParameter parameter) {
//        if (currentParameterIndex == 1) {
//            options = parameter.getDocument();
//        }
//    }
//
//    @Override
//    protected void resetQueryData() {
//        collectionName = "";
//        options.clear();
//    }
}
