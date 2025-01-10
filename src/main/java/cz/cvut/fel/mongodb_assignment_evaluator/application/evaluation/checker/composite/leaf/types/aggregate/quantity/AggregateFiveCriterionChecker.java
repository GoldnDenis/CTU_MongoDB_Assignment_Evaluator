package cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.composite.leaf.types.aggregate.quantity;

import cz.cvut.fel.mongodb_assignment_evaluator.enums.Criteria;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.InsertedDocumentStorage;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.composite.leaf.CheckerLeaf;
import cz.cvut.fel.mongodb_assignment_evaluator.application.model.query.type.AggregateQuery;

public class AggregateFiveCriterionChecker extends CheckerLeaf<AggregateQuery> {
    public AggregateFiveCriterionChecker(InsertedDocumentStorage documentStorage) {
        super(Criteria.AGGREGATE_FIVE, AggregateQuery.class, documentStorage);
    }

    @Override
    public void concreteCheck(AggregateQuery query) {
        if (query.getAggregationPipeline().stream().anyMatch(d -> !d.isEmpty())) {
            currentScore++;
        }
    }
}
