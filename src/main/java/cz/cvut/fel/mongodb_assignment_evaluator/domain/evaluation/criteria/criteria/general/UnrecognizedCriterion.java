package cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.criteria.criteria.general;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.enums.Operators;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.criteria.criteria.EvaluationCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type.QueryToken;

public class UnrecognizedCriterion extends EvaluationCriterion<QueryToken> {
    public UnrecognizedCriterion(int priority) {
        super(QueryToken.class, priority);
    }

    @Override
    protected void evaluate(QueryToken query) {
        if (query.getType().equals(Operators.UNRECOGNIZED)) {
            currentScore++;
        }
    }
}
