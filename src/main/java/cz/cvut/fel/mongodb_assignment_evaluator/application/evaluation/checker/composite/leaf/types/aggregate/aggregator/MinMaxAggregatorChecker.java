package cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.composite.leaf.types.aggregate.aggregator;

import cz.cvut.fel.mongodb_assignment_evaluator.enums.Aggregators;
import cz.cvut.fel.mongodb_assignment_evaluator.enums.Criteria;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.InsertedDocumentStorage;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.composite.leaf.CheckerLeaf;
import cz.cvut.fel.mongodb_assignment_evaluator.application.model.query.type.AggregateQuery;

public class MinMaxAggregatorChecker extends CheckerLeaf<AggregateQuery> {
    public MinMaxAggregatorChecker(InsertedDocumentStorage documentStorage) {
        super(Criteria.AGGREGATE_MIN_MAX_AGGREGATOR, AggregateQuery.class, documentStorage);
    }

    @Override
    public void concreteCheck(AggregateQuery query) {
        if (query.containsAggregator(Aggregators.MIN) ||
                query.containsAggregator(Aggregators.MAX)) {
            currentScore++;
        }
    }
}
