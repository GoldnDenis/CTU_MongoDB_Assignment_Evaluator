package cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.criteria.criteria.find;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.criteria.criteria.EvaluationCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.entity.Criterion;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type.FindQueryToken;

public class ProjectionCriterion extends EvaluationCriterion<FindQueryToken> {
    private final boolean needPositive;

    public ProjectionCriterion(Criterion criterion, int priority, boolean needPositive) {
        super(FindQueryToken.class, criterion, priority);
        this.needPositive = needPositive;
    }

    @Override
    protected void evaluate(FindQueryToken query) {
        if (!query.isTrivial()) {
            currentScore = query.containsProjection(needPositive) ? 1 : 0;
        }
    }
}
