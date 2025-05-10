package cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.criteria.criteria.general;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.enums.Operators;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.criteria.criteria.EvaluationCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.entity.Criterion;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type.QueryToken;

public class OperatorEvalCriterion<Q extends QueryToken> extends EvaluationCriterion<Q> {
    private final String expectedOperator;

    public OperatorEvalCriterion(Class<Q> queryType, Criterion criterion, int priority, Operators expectedOperator) {
        super(queryType, criterion, priority);
        this.expectedOperator = expectedOperator.getOperator();
    }

    @Override
    public void evaluate(QueryToken query) {
        if (!query.isTrivial()) {
            currentScore = query.operatorEquals(expectedOperator) ? 1 : 0;
        }
    }
}
