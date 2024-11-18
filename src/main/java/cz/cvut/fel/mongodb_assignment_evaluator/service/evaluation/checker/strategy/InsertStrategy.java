package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.strategy;

import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.insert.InsertInterlinkCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.insert.InsertTenDocumentsCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.insert.document.InsertDocumentArraysCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.insert.document.InsertDocumentEmbeddedObjectsCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.insert.usage.InsertManyUsedCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.insert.usage.InsertOneUsedCriterion;

public class InsertStrategy extends CheckerStrategy{
    public InsertStrategy() {
        criteria.add(new InsertTenDocumentsCriterion());
        criteria.add(new InsertDocumentEmbeddedObjectsCriterion());
        criteria.add(new InsertDocumentArraysCriterion());
        criteria.add(new InsertInterlinkCriterion());
        criteria.add(new InsertOneUsedCriterion());
        criteria.add(new InsertManyUsedCriterion());
    }
}
