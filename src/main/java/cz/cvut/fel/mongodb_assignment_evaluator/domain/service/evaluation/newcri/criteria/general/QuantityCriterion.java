package cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.newcri.criteria.general;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type.Query;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.newcri.EvaluationCriterion;

public class QuantityCriterion<Q extends Query> extends EvaluationCriterion<Q> {
    public QuantityCriterion(Class<Q> queryType) {
        super(queryType);
    }

    @Override
    protected void evaluate(Q query) {
        if (query.isTrivial()) {
            return;
        }
        currentScore++;
    }
}
