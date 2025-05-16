package cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.grader.criteria.update;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.grader.criteria.EvaluationCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.entity.Criterion;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type.UpdateQueryToken;

public class UpsertCriterion extends EvaluationCriterion<UpdateQueryToken> {
    private final boolean needPositive;

    public UpsertCriterion(Criterion criterion, boolean needPositive) {
        super(UpdateQueryToken.class, criterion);
        this.needPositive = needPositive;
    }

    protected void evaluate(UpdateQueryToken query) {
        currentScore = query.containsUpsert(needPositive) ? 1 : 0;
    }
}
