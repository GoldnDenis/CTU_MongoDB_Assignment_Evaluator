package cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.newcri.criteria.general;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type.Query;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.newcri.EvaluationCriterion;

public class OperatorEvalCriterion<Q extends Query> extends EvaluationCriterion<Q> {
    private final String expectedOperator;

    public OperatorEvalCriterion(Class<Q> queryType, String expectedOperator) {
        super(queryType);
        this.expectedOperator = expectedOperator;
    }

    @Override
    public void evaluate(Query query) {
        String operator = query.getOperator();
        if (operator != null && operator.equalsIgnoreCase(expectedOperator)) {
            currentScore++;
        }
    }
}
