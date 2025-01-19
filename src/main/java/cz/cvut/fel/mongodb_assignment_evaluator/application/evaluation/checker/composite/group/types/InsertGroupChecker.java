package cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.composite.group.types;

import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.InsertedDocumentStorage;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.composite.leaf.CheckerLeaf;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.composite.group.CriteriaGroupChecker;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.composite.leaf.types.insert.quantity.InsertDocumentsChecker;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.composite.leaf.types.insert.document.InsertArraysChecker;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.composite.leaf.types.insert.document.InsertEmbeddedChecker;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.composite.leaf.types.insert.many.InsertManyChecker;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.composite.leaf.types.insert.one.InsertOneChecker;
import cz.cvut.fel.mongodb_assignment_evaluator.application.model.query.type.InsertQuery;

import java.util.List;

public class InsertGroupChecker extends CriteriaGroupChecker<InsertQuery> {
    public InsertGroupChecker(InsertedDocumentStorage documentStorage) {
        super(documentStorage);
    }

    @Override
    protected List<CheckerLeaf<InsertQuery>> initCriteria() {
        return List.of(
                new InsertDocumentsChecker(documentStorage),
                new InsertEmbeddedChecker(documentStorage),
                new InsertArraysChecker(documentStorage),
                new InsertOneChecker(documentStorage),
                new InsertManyChecker(documentStorage)
        );
    }
}
