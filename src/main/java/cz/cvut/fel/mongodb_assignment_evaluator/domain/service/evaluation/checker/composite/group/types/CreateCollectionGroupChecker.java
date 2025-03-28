package cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.checker.composite.group.types;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type.CreateCollectionQuery;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.checker.InsertedDocumentStorage;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.checker.composite.group.CriteriaGroupChecker;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.checker.composite.leaf.CheckerLeaf;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.checker.composite.leaf.types.collection.CreateCollectionChecker;

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
