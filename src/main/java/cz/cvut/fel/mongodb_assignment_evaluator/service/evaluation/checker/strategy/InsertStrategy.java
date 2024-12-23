package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.strategy;

import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.InsertedDocumentStorage;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.composite.specific.insert.InsertInterlinkCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.composite.specific.insert.InsertTenDocumentsCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.composite.specific.insert.InsertDocumentArraysCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.composite.specific.insert.InsertDocumentEmbeddedObjectsCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.composite.specific.insert.InsertManyUsedCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.composite.specific.insert.InsertOneUsedCriterion;

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
