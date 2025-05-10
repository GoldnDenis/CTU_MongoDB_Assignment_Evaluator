package cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.criteria.criteria.aggregate;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.enums.Aggregations;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.criteria.criteria.EvaluationCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.entity.Criterion;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type.AggregateQueryToken;

public class AggregationCriterion extends EvaluationCriterion<AggregateQueryToken> {
    private final Aggregations requiredAggregations;

    public AggregationCriterion(Criterion criterion, int priority, Aggregations requiredAggregations) {
        super(AggregateQueryToken.class, criterion, priority);
        this.requiredAggregations = requiredAggregations;
    }

    @Override
    protected void evaluate(AggregateQueryToken query) {
        currentScore = query.containsAggregations(requiredAggregations) ? 1 : 0;
    }
}
