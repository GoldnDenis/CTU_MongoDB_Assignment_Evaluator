package cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.checker.composite.leaf.types.aggregate.quantity;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.enums.Criteria;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type.AggregateQuery;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.checker.InsertedDocumentStorage;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.checker.composite.leaf.CheckerLeaf;

public class AggregateFiveChecker extends CheckerLeaf<AggregateQuery> {
    public AggregateFiveChecker(InsertedDocumentStorage documentStorage) {
        super(Criteria.AGGREGATE_FIVE, AggregateQuery.class, documentStorage);
    }

    @Override
    public void concreteCheck(AggregateQuery query) {
        if (query.getAggregationPipeline().stream().anyMatch(d -> !d.isEmpty())) {
            currentScore++;
        }
    }
}
