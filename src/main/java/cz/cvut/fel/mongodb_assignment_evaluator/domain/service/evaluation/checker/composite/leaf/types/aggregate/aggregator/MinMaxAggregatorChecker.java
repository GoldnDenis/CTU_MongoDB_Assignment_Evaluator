package cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.checker.composite.leaf.types.aggregate.aggregator;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.enums.Aggregators;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.enums.Criteria;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type.AggregateQuery;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.checker.InsertedDocumentStorage;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.checker.composite.leaf.CheckerLeaf;

public class MinMaxAggregatorChecker extends CheckerLeaf<AggregateQuery> {
    public MinMaxAggregatorChecker(InsertedDocumentStorage documentStorage) {
        super(Criteria.AGGREGATOR_MIN_MAX, AggregateQuery.class, documentStorage);
    }

    @Override
    public void concreteCheck(AggregateQuery query) {
        if (query.containsAggregator(Aggregators.MIN) ||
                query.containsAggregator(Aggregators.MAX)) {
            currentScore++;
        }
    }
}
