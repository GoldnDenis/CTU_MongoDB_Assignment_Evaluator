package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.strategy;

import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.aggregate.AggregateFiveCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.aggregate.aggregator.AggregateCountAggregatorCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.aggregate.aggregator.AggregateFirstLastAggregatorCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.aggregate.aggregator.AggregateMinMaxAggregatorCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.aggregate.aggregator.AggregateSumAvgAggregatorCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.aggregate.stage.*;

public class AggregateStrategy extends CheckerStrategy{
    public AggregateStrategy() {
        criteria.add(new AggregateFiveCriterion());
        criteria.add(new AggregateMatchStageCriterion());
        criteria.add(new AggregateGroupStageCriterion());
        criteria.add(new AggregateSortStageCriterion());
        criteria.add(new AggregateProjectAddFieldsStageCriterion());
        criteria.add(new AggregateSkipStageCriterion());
        criteria.add(new AggregateLimitStageCriterion());
        criteria.add(new AggregateSumAvgAggregatorCriterion());
        criteria.add(new AggregateCountAggregatorCriterion());
        criteria.add(new AggregateMinMaxAggregatorCriterion());
        criteria.add(new AggregateFirstLastAggregatorCriterion());
    }
}
