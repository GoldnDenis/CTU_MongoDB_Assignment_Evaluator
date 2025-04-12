package cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.criteria.criteria.general;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.enums.Operators;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.modifier.QueryModifier;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type.Query;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.criteria.EvaluationCriterion;

import java.util.List;

public class ModifierOperatorCriterion<Q extends Query> extends EvaluationCriterion<Q> {
    private final String expectedOperator;

    public ModifierOperatorCriterion(Class<Q> queryType, int priority, Operators expectedOperator) {
        super(queryType, priority);
        this.expectedOperator = expectedOperator.getOperator();
    }

    @Override
    public void evaluate(Query query) {
        List<QueryModifier> modifiers = query.getModifiers();
        if (modifiers.isEmpty() ||
            query.isTrivial()) {
            return;
        }
        for (QueryModifier modifier : modifiers) {
            if (modifier.getOperator().equalsIgnoreCase(expectedOperator)) {
                currentScore++;
                break;
            }
        }
    }
}
