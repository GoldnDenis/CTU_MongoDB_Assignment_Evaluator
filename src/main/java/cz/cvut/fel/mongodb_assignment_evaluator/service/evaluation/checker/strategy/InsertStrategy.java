package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.strategy;

import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.MockMongoDB;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.insert.InsertInterlinkCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.insert.InsertTenDocumentsCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.insert.document.InsertDocumentArraysCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.insert.document.InsertDocumentEmbeddedObjectsCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.insert.usage.InsertManyUsedCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.insert.usage.InsertOneUsedCriterion;

public class InsertStrategy extends CheckerStrategy{
    public InsertStrategy(MockMongoDB mockDb) {
        super(mockDb);
        criteria.add(new InsertTenDocumentsCriterion(mockDb));
        criteria.add(new InsertDocumentEmbeddedObjectsCriterion(mockDb));
        criteria.add(new InsertDocumentArraysCriterion(mockDb));
        criteria.add(new InsertInterlinkCriterion(mockDb));
        criteria.add(new InsertOneUsedCriterion(mockDb));
        criteria.add(new InsertManyUsedCriterion(mockDb));
    }
}
