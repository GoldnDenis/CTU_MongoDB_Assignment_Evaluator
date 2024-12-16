package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.strategy;

import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.MockMongoDB;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.collection.CreateOneCollectionCriterion;

import java.util.List;

public class CreateCollectionStrategy extends CheckerStrategy {
    public CreateCollectionStrategy(MockMongoDB mockDb) {
        super(mockDb, List.of(
                        new CreateOneCollectionCriterion(mockDb)
                ));
    }
}
