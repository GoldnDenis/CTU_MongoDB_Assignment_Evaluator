package cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.grader.criteria.find;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.enums.QuerySelectors;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.grader.criteria.EvaluationCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.entity.Criterion;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type.FindQueryToken;

public class FindSelectorCriterion extends EvaluationCriterion<FindQueryToken> {
    private final QuerySelectors requiredSelectors;

    public FindSelectorCriterion(Criterion criterion, QuerySelectors requiredSelectors) {
        super(FindQueryToken.class, criterion);
        this.requiredSelectors = requiredSelectors;
    }

    @Override
    protected void evaluate(FindQueryToken query) {
        if (!query.isTrivial()) {
            currentScore = query.containsSelectors(requiredSelectors) ? 1 : 0;
        }
    }
}
