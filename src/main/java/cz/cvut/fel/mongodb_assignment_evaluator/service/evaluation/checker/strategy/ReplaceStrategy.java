package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.strategy;

import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.InsertedDocumentStorage;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.composite.specific.replace.ReplaceValueCriterion;

import java.util.List;

public class ReplaceStrategy extends CheckerStrategy{
    public ReplaceStrategy(InsertedDocumentStorage mockDb) {
        super(mockDb, List.of(
                new ReplaceValueCriterion(mockDb)
        ));
    }
}
