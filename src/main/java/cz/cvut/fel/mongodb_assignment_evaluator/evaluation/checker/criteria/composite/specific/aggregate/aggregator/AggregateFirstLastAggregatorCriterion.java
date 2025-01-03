package cz.cvut.fel.mongodb_assignment_evaluator.evaluation.checker.criteria.composite.specific.aggregate.aggregator;

import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.model.enums.Aggregators;
import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.model.enums.Criteria;
import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.checker.InsertedDocumentStorage;
import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.checker.criteria.AssignmentCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.model.query.type.AggregateQuery;

public class AggregateFirstLastAggregatorCriterion extends AssignmentCriterion<AggregateQuery> {
    public AggregateFirstLastAggregatorCriterion(InsertedDocumentStorage documentStorage) {
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
