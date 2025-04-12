package cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.criteria.criteria.general;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.enums.Operators;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type.Query;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.criteria.EvaluationCriterion;

public class OperatorEvalCriterion<Q extends Query> extends EvaluationCriterion<Q> {
    private final String expectedOperator;

    public OperatorEvalCriterion(Class<Q> queryType, int priority, Operators expectedOperator) {
        super(queryType, priority);
        this.expectedOperator = expectedOperator.getOperator();
    }

    @Override
    public void evaluate(Query query) {
        currentScore = query.operatorEquals(expectedOperator) ? 1 : 0;
    }
}
