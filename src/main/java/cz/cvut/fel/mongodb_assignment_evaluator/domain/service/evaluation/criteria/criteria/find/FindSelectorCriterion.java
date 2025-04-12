package cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.criteria.criteria.find;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.enums.QuerySelectors;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type.FindQuery;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.criteria.EvaluationCriterion;

public class FindSelectorCriterion extends EvaluationCriterion<FindQuery> {
    private final QuerySelectors requiredSelectors;

    public FindSelectorCriterion(int priority, QuerySelectors requiredSelectors) {
        super(FindQuery.class, priority);
        this.requiredSelectors = requiredSelectors;
    }

    @Override
    protected void evaluate(FindQuery query) {
        if (!query.isTrivial()) {
            currentScore = query.containsSelectors(requiredSelectors) ? 1 : 0;
        }
    }
}
