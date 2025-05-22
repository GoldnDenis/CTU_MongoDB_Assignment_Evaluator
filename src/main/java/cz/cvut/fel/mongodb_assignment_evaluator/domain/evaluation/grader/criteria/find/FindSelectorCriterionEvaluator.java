package cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.grader.criteria.find;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.enums.QuerySelectors;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.grader.criteria.CriterionEvaluator;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.entity.Criterion;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type.FindQuery;

/**
 * Inspects filter documents for the presence of required selector operators.
 */
public class FindSelectorCriterionEvaluator extends CriterionEvaluator<FindQuery> {
    private final QuerySelectors requiredSelectors;

    public FindSelectorCriterionEvaluator(Criterion criterion, QuerySelectors requiredSelectors) {
        super(FindQuery.class, criterion);
        this.requiredSelectors = requiredSelectors;
    }

    @Override
    protected void evaluate(FindQuery query) {
        if (!query.isTrivial()) {
            currentScore = query.containsSelectors(requiredSelectors) ? 1 : 0;
        }
    }
}
