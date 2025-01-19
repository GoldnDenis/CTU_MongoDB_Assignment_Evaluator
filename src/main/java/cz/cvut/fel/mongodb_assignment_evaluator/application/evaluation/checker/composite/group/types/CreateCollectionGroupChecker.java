package cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.composite.group.types;

import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.InsertedDocumentStorage;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.composite.leaf.CheckerLeaf;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.composite.group.CriteriaGroupChecker;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.composite.leaf.types.collection.CreateCollectionChecker;
import cz.cvut.fel.mongodb_assignment_evaluator.application.model.query.type.CreateCollectionQuery;

import java.util.List;

public class CreateCollectionGroupChecker extends CriteriaGroupChecker<CreateCollectionQuery> {
    public CreateCollectionGroupChecker(InsertedDocumentStorage documentStorage) {
        super(documentStorage);
    }

    @Override
    protected List<CheckerLeaf<CreateCollectionQuery>> initCriteria() {
        return List.of(
                new CreateCollectionChecker(documentStorage)
        );
    }
}
