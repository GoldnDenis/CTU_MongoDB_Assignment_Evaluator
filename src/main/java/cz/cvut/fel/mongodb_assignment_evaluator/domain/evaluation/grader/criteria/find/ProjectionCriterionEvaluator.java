package cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.grader.criteria.find;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.grader.criteria.CriterionEvaluator;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.entity.Criterion;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type.FindQuery;

/**
 * Verifies that projections are non-trivial and include either all zeroes or all ones (excluding _id).
 * The query itself has to be non-trivial as well.
 */
public class ProjectionCriterionEvaluator extends CriterionEvaluator<FindQuery> {
    private final boolean needPositive;

    public ProjectionCriterionEvaluator(Criterion criterion, boolean needPositive) {
        super(FindQuery.class, criterion);
        this.needPositive = needPositive;
    }

    @Override
    protected void evaluate(FindQuery query) {
        if (!query.isTrivial()) {
            currentScore = query.containsProjection(needPositive) ? 1 : 0;
        }
    }
}
