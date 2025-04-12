package cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.criteria.criteria.find;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type.FindQuery;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.criteria.EvaluationCriterion;

public class ProjectionCriterion extends EvaluationCriterion<FindQuery> {
    private final boolean needPositive;

    public ProjectionCriterion(int priority, boolean needPositive) {
        super(FindQuery.class, priority);
        this.needPositive = needPositive;
    }

    @Override
    protected void evaluate(FindQuery query) {
        if (!query.isTrivial()) {
            currentScore = query.containsProjection(needPositive) ? 1 : 0;
        }
    }
}
