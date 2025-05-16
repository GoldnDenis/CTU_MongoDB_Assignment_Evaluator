package cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.grader.criteria.find;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.grader.criteria.EvaluationCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.entity.Criterion;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type.FindQueryToken;

public class ProjectionCriterion extends EvaluationCriterion<FindQueryToken> {
    private final boolean needPositive;

    public ProjectionCriterion(Criterion criterion, boolean needPositive) {
        super(FindQueryToken.class, criterion);
        this.needPositive = needPositive;
    }

    @Override
    protected void evaluate(FindQueryToken query) {
        if (!query.isTrivial()) {
            currentScore = query.containsProjection(needPositive) ? 1 : 0;
        }
    }
}
