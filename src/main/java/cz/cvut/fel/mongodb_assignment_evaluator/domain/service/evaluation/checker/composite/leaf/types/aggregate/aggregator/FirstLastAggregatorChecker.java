package cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.checker.composite.leaf.types.aggregate.aggregator;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.enums.Aggregators;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.enums.Criteria;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type.AggregateQuery;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.checker.InsertedDocumentStorage;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.checker.composite.leaf.CheckerLeaf;

public class FirstLastAggregatorChecker extends CheckerLeaf<AggregateQuery> {
    public FirstLastAggregatorChecker(InsertedDocumentStorage documentStorage) {
        super(Criteria.AGGREGATOR_FIRST_LAST, AggregateQuery.class, documentStorage);
    }

    @Override
    public void concreteCheck(AggregateQuery query) {
        if (query.containsAggregator(Aggregators.FIRST) ||
            query.containsAggregator(Aggregators.LAST)) {
            currentScore++;
        }
    }
}
