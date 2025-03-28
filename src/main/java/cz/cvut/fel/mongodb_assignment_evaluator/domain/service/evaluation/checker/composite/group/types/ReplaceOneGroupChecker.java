package cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.checker.composite.group.types;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type.ReplaceOneQuery;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.checker.InsertedDocumentStorage;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.checker.composite.group.CriteriaGroupChecker;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.checker.composite.leaf.CheckerLeaf;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.checker.composite.leaf.types.replace.ReplaceValueChecker;

import java.util.List;

public class ReplaceOneGroupChecker extends CriteriaGroupChecker<ReplaceOneQuery> {
    public ReplaceOneGroupChecker(InsertedDocumentStorage documentStorage) {
        super(documentStorage);
    }

    @Override
    protected List<CheckerLeaf<ReplaceOneQuery>> initCriteria() {
        return List.of(
                new ReplaceValueChecker(documentStorage)
        );
    }
}
