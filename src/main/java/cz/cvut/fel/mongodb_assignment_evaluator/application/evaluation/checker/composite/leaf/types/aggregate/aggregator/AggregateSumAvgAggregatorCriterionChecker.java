package cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.composite.leaf.types.aggregate.aggregator;

import cz.cvut.fel.mongodb_assignment_evaluator.enums.Aggregators;
import cz.cvut.fel.mongodb_assignment_evaluator.enums.Criteria;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.InsertedDocumentStorage;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.composite.leaf.CheckerLeaf;
import cz.cvut.fel.mongodb_assignment_evaluator.application.model.query.type.AggregateQuery;

public class AggregateSumAvgAggregatorCriterionChecker extends CheckerLeaf<AggregateQuery> {
    public AggregateSumAvgAggregatorCriterionChecker(InsertedDocumentStorage documentStorage) {
        super(Criteria.AGGREGATE_SUM_AVG_AGGREGATOR, AggregateQuery.class, documentStorage);
    }

    @Override
    public void concreteCheck(AggregateQuery query) {
        if (query.containsAggregator(Aggregators.SUM) ||
                query.containsAggregator(Aggregators.AVG)) {
            currentScore++;
        }
    }
}
