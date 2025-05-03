package cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.criteria.criteria.find;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.enums.QuerySelectors;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.criteria.criteria.EvaluationCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type.FindQueryToken;

public class FindSelectorCriterion extends EvaluationCriterion<FindQueryToken> {
    private final QuerySelectors requiredSelectors;

    public FindSelectorCriterion(int priority, QuerySelectors requiredSelectors) {
        super(FindQueryToken.class, priority);
        this.requiredSelectors = requiredSelectors;
    }

    @Override
    protected void evaluate(FindQueryToken query) {
        if (!query.isTrivial()) {
            currentScore = query.containsSelectors(requiredSelectors) ? 1 : 0;
        }
    }
}
