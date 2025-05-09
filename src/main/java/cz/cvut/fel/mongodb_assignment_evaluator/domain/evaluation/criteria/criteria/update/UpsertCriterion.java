package cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.criteria.criteria.update;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.criteria.criteria.EvaluationCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.entity.Criterion;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type.UpdateQueryToken;

public class UpsertCriterion extends EvaluationCriterion<UpdateQueryToken> {
    private final boolean needPositive;

    public UpsertCriterion(Criterion criterion, int priority, boolean needPositive) {
        super(UpdateQueryToken.class, criterion, priority);
        this.needPositive = needPositive;
    }

    protected void evaluate(UpdateQueryToken query) {
        currentScore = query.containsUpsert(needPositive) ? 1 : 0;
    }
}
