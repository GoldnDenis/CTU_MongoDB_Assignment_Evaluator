package cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.criteria.criteria.general;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.criteria.criteria.EvaluationCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.entity.Criterion;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type.QueryToken;

public class QuantityCriterion<Q extends QueryToken> extends EvaluationCriterion<Q> {
    public QuantityCriterion(Class<Q> queryType, Criterion criterion, int priority) {
        super(queryType, criterion, priority);
    }

    @Override
    protected void evaluate(Q query) {
        currentScore = query.getQueryResultCount();
    }
}
