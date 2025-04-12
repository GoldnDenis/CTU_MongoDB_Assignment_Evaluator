package cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.criteria.criteria.update;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type.UpdateQuery;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.criteria.EvaluationCriterion;

public class UpsertCriterion extends EvaluationCriterion<UpdateQuery> {
    private final boolean needPositive;

    public UpsertCriterion(int priority, boolean needPositive) {
        super(UpdateQuery.class, priority);
        this.needPositive = needPositive;
    }

    protected void evaluate(UpdateQuery query) {
        currentScore = query.containsUpsert(needPositive) ? 1 : 0;
    }
}
