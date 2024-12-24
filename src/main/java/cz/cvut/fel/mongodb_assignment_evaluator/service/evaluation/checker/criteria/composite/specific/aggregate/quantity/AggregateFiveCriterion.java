package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.composite.specific.aggregate.quantity;

import cz.cvut.fel.mongodb_assignment_evaluator.service.enums.Criteria;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.InsertedDocumentStorage;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.AssignmentCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.query.type.AggregateQuery;

public class AggregateFiveCriterion extends AssignmentCriterion<AggregateQuery> {
    public AggregateFiveCriterion(InsertedDocumentStorage documentStorage) {
        super(Criteria.AGGREGATE_FIVE, AggregateQuery.class, documentStorage);
    }

    @Override
    public void concreteCheck(AggregateQuery query) {
        if (query.getAggregationPipeline().stream().anyMatch(d -> !d.isTrivial())) {
            currentScore++;
        }
    }
}
