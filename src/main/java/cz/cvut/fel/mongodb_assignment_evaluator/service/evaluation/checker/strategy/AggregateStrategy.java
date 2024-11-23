package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.strategy;

import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.MockMongoDB;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.aggregate.AggregateFiveCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.aggregate.aggregator.AggregateCountAggregatorCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.aggregate.aggregator.AggregateFirstLastAggregatorCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.aggregate.aggregator.AggregateMinMaxAggregatorCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.aggregate.aggregator.AggregateSumAvgAggregatorCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.aggregate.stage.*;

public class AggregateStrategy extends CheckerStrategy{

    public AggregateStrategy(MockMongoDB mockDb) {
        super(mockDb);
        criteria.add(new AggregateFiveCriterion(mockDb));
        criteria.add(new AggregateMatchStageCriterion(mockDb));
        criteria.add(new AggregateGroupStageCriterion(mockDb));
        criteria.add(new AggregateSortStageCriterion(mockDb));
        criteria.add(new AggregateProjectAddFieldsStageCriterion(mockDb));
        criteria.add(new AggregateSkipStageCriterion(mockDb));
        criteria.add(new AggregateLimitStageCriterion(mockDb));
        criteria.add(new AggregateSumAvgAggregatorCriterion(mockDb));
        criteria.add(new AggregateCountAggregatorCriterion(mockDb));
        criteria.add(new AggregateMinMaxAggregatorCriterion(mockDb));
        criteria.add(new AggregateFirstLastAggregatorCriterion(mockDb));
        criteria.add(new AggregateLookupUsedCriterion(mockDb));
    }
}
