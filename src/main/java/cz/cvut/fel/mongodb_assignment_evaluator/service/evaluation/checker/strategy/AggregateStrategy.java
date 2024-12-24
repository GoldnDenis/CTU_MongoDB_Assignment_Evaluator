package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.strategy;

import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.InsertedDocumentStorage;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.composite.specific.aggregate.aggregator.AggregateCountAggregatorCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.composite.specific.aggregate.aggregator.AggregateFirstLastAggregatorCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.composite.specific.aggregate.aggregator.AggregateMinMaxAggregatorCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.composite.specific.aggregate.aggregator.AggregateSumAvgAggregatorCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.composite.specific.aggregate.quantity.AggregateFiveCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.composite.specific.aggregate.stage.*;

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
                new AggregateLookupStageCriterion(mockDb)
        ));
    }
}
