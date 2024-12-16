package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.strategy;

import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.MockMongoDB;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.insert.InsertInterlinkCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.insert.InsertTenDocumentsCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.insert.document.InsertDocumentArraysCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.insert.document.InsertDocumentEmbeddedObjectsCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.insert.usage.InsertManyUsedCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.insert.usage.InsertOneUsedCriterion;

import java.util.List;

public class InsertStrategy extends CheckerStrategy {
    public InsertStrategy(MockMongoDB mockDb) {
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
