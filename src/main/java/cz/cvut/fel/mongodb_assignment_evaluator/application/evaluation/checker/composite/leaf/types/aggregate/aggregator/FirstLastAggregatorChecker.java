package cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.composite.leaf.types.aggregate.aggregator;

import cz.cvut.fel.mongodb_assignment_evaluator.enums.Aggregators;
import cz.cvut.fel.mongodb_assignment_evaluator.enums.Criteria;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.InsertedDocumentStorage;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.composite.leaf.CheckerLeaf;
import cz.cvut.fel.mongodb_assignment_evaluator.application.model.query.type.AggregateQuery;

public class FirstLastAggregatorChecker extends CheckerLeaf<AggregateQuery> {
    public FirstLastAggregatorChecker(InsertedDocumentStorage documentStorage) {
        super(Criteria.AGGREGATE_FIRST_LAST_AGGREGATOR, AggregateQuery.class, documentStorage);
    }

    @Override
    public void concreteCheck(AggregateQuery query) {
        if (query.containsAggregator(Aggregators.FIRST) ||
            query.containsAggregator(Aggregators.LAST)) {
            currentScore++;
        }
    }
}
