package cz.cvut.fel.task_evaluator.evaluation.checker.strategy;

import cz.cvut.fel.task_evaluator.evaluation.checker.criteria.collection.CreateCollectionCriterion;

public class CreateCollectionStrategy extends CheckerStrategy {
    public CreateCollectionStrategy() {
        criteria.add(new CreateCollectionCriterion());
    }
}
