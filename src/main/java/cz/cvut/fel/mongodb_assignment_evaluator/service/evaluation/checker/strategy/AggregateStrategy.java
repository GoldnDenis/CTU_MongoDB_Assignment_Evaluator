package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.strategy;

import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.MockMongoDB;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.aggregate.AggregateFiveCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.aggregate.aggregator.AggregateCountAggregatorCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.aggregate.aggregator.AggregateFirstLastAggregatorCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.aggregate.aggregator.AggregateMinMaxAggregatorCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.aggregate.aggregator.AggregateSumAvgAggregatorCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.aggregate.stage.*;

import java.util.List;

public class AggregateStrategy extends CheckerStrategy{
    public AggregateStrategy(MockMongoDB mockDb) {
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
