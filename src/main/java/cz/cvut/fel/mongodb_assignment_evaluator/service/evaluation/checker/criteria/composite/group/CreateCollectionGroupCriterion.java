package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.composite.group;

import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.InsertedDocumentStorage;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.AssignmentCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.composite.specific.collection.CreateOneCollectionCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.query.type.CreateCollectionQuery;

import java.util.List;

//@Getter
public class CreateCollectionGroupCriterion extends GroupCriterion<CreateCollectionQuery> {
    public CreateCollectionGroupCriterion(InsertedDocumentStorage documentStorage) {
        super(documentStorage);
    }

    @Override
    protected List<AssignmentCriterion<CreateCollectionQuery>> initCriteria() {
        return List.of(
                new CreateOneCollectionCriterion(documentStorage)
        );
    }
}
