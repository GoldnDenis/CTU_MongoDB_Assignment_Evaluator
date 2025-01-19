package cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.composite.group.types;

import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.InsertedDocumentStorage;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.composite.leaf.CheckerLeaf;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.composite.group.CriteriaGroupChecker;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.composite.leaf.types.replace.ReplaceValueChecker;
import cz.cvut.fel.mongodb_assignment_evaluator.application.model.query.type.ReplaceOneQuery;

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
