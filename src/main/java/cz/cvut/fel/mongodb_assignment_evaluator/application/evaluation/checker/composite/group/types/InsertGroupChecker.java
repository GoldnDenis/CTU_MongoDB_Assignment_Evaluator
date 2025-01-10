package cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.composite.group.types;

import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.InsertedDocumentStorage;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.composite.leaf.CheckerLeaf;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.composite.group.CriteriaGroupChecker;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.composite.leaf.types.insert.quantity.InsertTenDocumentsCriterionChecker;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.composite.leaf.types.insert.document.InsertDocumentArraysCriterionChecker;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.composite.leaf.types.insert.document.InsertDocumentEmbeddedObjectsCriterionChecker;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.composite.leaf.types.insert.many.InsertManyUsedCriterionChecker;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.composite.leaf.types.insert.one.InsertOneUsedCriterionChecker;
import cz.cvut.fel.mongodb_assignment_evaluator.application.model.query.type.InsertQuery;

import java.util.List;

public class InsertGroupChecker extends CriteriaGroupChecker<InsertQuery> {
    public InsertGroupChecker(InsertedDocumentStorage documentStorage) {
        super(documentStorage);
    }

    @Override
    protected List<CheckerLeaf<InsertQuery>> initCriteria() {
        return List.of(
                new InsertTenDocumentsCriterionChecker(documentStorage),
                new InsertDocumentEmbeddedObjectsCriterionChecker(documentStorage),
                new InsertDocumentArraysCriterionChecker(documentStorage),
                new InsertOneUsedCriterionChecker(documentStorage),
                new InsertManyUsedCriterionChecker(documentStorage)
        );
    }
}
