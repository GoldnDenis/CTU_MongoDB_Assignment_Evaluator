package cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.composite.group.types;

import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.InsertedDocumentStorage;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.composite.leaf.CheckerLeaf;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.composite.group.CriteriaGroupChecker;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.composite.leaf.types.general.CommentChecker;
import cz.cvut.fel.mongodb_assignment_evaluator.application.model.query.type.Query;

import java.util.List;

public class GeneralChecker extends CriteriaGroupChecker<Query> {
    public GeneralChecker(InsertedDocumentStorage documentStorage) {
        super(documentStorage);
    }

    @Override
    protected List<CheckerLeaf<Query>> initCriteria() {
        return List.of(
                new CommentChecker(documentStorage)
        );
    }
}
