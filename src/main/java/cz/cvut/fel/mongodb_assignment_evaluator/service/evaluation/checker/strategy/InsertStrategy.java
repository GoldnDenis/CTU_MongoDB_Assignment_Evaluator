package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.strategy;

import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.InsertedDocumentStorage;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.composite.specific.insert.InsertInterlinkCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.composite.specific.insert.quantity.InsertTenDocumentsCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.composite.specific.insert.document.InsertDocumentArraysCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.composite.specific.insert.document.InsertDocumentEmbeddedObjectsCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.composite.specific.insert.many.InsertManyUsedCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.composite.specific.insert.one.InsertOneUsedCriterion;

import java.util.List;

public class InsertStrategy extends CheckerStrategy {
    public InsertStrategy(InsertedDocumentStorage mockDb) {
        super(mockDb, List.of(
                new InsertTenDocumentsCriterion(mockDb),
                new InsertDocumentEmbeddedObjectsCriterion(mockDb),
                new InsertDocumentArraysCriterion(mockDb),
                new InsertInterlinkCriterion(mockDb),
                new InsertOneUsedCriterion(mockDb),
                new InsertManyUsedCriterion(mockDb)
        ));
    }
}
