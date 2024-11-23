package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.strategy;

import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.MockMongoDB;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.replace.ReplaceValueCriterion;

public class ReplaceStrategy extends CheckerStrategy{
    public ReplaceStrategy(MockMongoDB mockDb) {
        super(mockDb);
        criteria.add(new ReplaceValueCriterion(mockDb));
    }
}
