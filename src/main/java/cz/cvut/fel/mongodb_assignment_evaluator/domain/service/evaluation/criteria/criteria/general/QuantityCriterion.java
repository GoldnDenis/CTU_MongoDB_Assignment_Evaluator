package cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.criteria.criteria.general;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type.Query;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.criteria.EvaluationCriterion;

public class QuantityCriterion<Q extends Query> extends EvaluationCriterion<Q> {
    public QuantityCriterion(Class<Q> queryType, int priority) {
        super(queryType, priority);
    }

    @Override
    protected void evaluate(Q query) {
        currentScore = query.getQueryResultCount();
    }
}
