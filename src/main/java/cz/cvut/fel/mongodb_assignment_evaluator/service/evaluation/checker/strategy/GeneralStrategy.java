package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.strategy;

import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.MockMongoDB;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.general.CommentCriterion;

import java.util.List;

public class GeneralStrategy extends CheckerStrategy{
    public GeneralStrategy(MockMongoDB mockDb) {
        super(mockDb, List.of(
                new CommentCriterion(mockDb)
        ));
    }
}
