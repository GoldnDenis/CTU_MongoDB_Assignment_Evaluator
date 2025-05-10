package cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.criteria.criteria.general;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.enums.Operators;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.criteria.criteria.EvaluationCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.entity.Criterion;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.modifier.QueryModifier;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type.QueryToken;

import java.util.List;

public class ModifierOperatorCriterion<Q extends QueryToken> extends EvaluationCriterion<Q> {
    private final String expectedOperator;

    public ModifierOperatorCriterion(Class<Q> queryType, Criterion criterion, int priority, Operators expectedOperator) {
        super(queryType, criterion, priority);
        this.expectedOperator = expectedOperator.getOperator();
    }

    @Override
    public void evaluate(QueryToken query) {
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
