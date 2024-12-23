package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.strategy;

import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.InsertedDocumentStorage;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.composite.specific.aggregate.*;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.composite.specific.aggregate.AggregateCountAggregatorCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.composite.specific.aggregate.AggregateFirstLastAggregatorCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.composite.specific.aggregate.AggregateMinMaxAggregatorCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.composite.specific.aggregate.AggregateSumAvgAggregatorCriterion;

import java.util.List;

public class AggregateStrategy extends CheckerStrategy{
    public AggregateStrategy(InsertedDocumentStorage mockDb) {
        super(mockDb, List.of(
                new AggregateFiveCriterion(mockDb),
                new AggregateMatchStageCriterion(mockDb),
                new AggregateGroupStageCriterion(mockDb),
                new AggregateSortStageCriterion(mockDb),
                new AggregateProjectAddFieldsStageCriterion(mockDb),
                new AggregateSkipStageCriterion(mockDb),
                new AggregateLimitStageCriterion(mockDb),
                new AggregateSumAvgAggregatorCriterion(mockDb),
                new AggregateCountAggregatorCriterion(mockDb),
                new AggregateMinMaxAggregatorCriterion(mockDb),
                new AggregateFirstLastAggregatorCriterion(mockDb),
                new AggregateLookupUsedCriterion(mockDb)
        ));
    }
}
