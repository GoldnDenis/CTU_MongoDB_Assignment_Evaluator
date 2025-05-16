package cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.grader.criteria.general;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.enums.Operators;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.grader.criteria.EvaluationCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.entity.Criterion;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type.QueryToken;

public class UnrecognizedCriterion extends EvaluationCriterion<QueryToken> {
    public UnrecognizedCriterion(Criterion criterion) {
        super(QueryToken.class, criterion);
    }

    @Override
    protected void evaluate(QueryToken query) {
        if (query.getType().equals(Operators.UNRECOGNIZED)) {
            currentScore++;
        }
    }
}
