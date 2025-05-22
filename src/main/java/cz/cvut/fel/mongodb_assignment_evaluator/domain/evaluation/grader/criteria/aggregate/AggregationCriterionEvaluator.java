package cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.grader.criteria.aggregate;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.enums.AggregationOperators;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.grader.criteria.CriterionEvaluator;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.entity.Criterion;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type.AggregateQuery;

/**
 * Scans the aggregation pipeline for required stages and operators, potentially limiting the search to specific pipeline levels.
 */
public class AggregationCriterionEvaluator extends CriterionEvaluator<AggregateQuery> {
    private final AggregationOperators requiredAggregationOperators;

    public AggregationCriterionEvaluator(Criterion criterion, AggregationOperators requiredAggregationOperators) {
        super(AggregateQuery.class, criterion);
        this.requiredAggregationOperators = requiredAggregationOperators;
    }

    @Override
    protected void evaluate(AggregateQuery query) {
        currentScore = query.containsAggregations(requiredAggregationOperators) ? 1 : 0;
    }
}
