package cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.criteria.criteria.aggregate;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.enums.Aggregations;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type.AggregateQuery;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.criteria.EvaluationCriterion;

public class AggregationCriterion extends EvaluationCriterion<AggregateQuery> {
    private final Aggregations requiredAggregations;

    public AggregationCriterion(int priority, Aggregations requiredAggregations) {
        super(AggregateQuery.class, priority);
        this.requiredAggregations = requiredAggregations;
    }

    @Override
    protected void evaluate(AggregateQuery query) {
        currentScore = query.containsAggregations(requiredAggregations) ? 1 : 0;
    }
}
