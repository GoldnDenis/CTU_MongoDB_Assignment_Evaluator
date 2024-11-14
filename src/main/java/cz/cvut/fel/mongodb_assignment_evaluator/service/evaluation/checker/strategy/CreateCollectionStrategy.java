package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.strategy;

import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.collection.CreateCollectionCriterion;

public class CreateCollectionStrategy extends CheckerStrategy {
    public CreateCollectionStrategy() {
        criteria.add(new CreateCollectionCriterion());
    }
}
