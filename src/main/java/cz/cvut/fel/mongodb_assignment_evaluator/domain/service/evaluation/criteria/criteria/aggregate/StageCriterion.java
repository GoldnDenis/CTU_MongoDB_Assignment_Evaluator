package cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.criteria.criteria.aggregate;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.enums.Stages;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type.AggregateQuery;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.criteria.EvaluationCriterion;

public class StageCriterion extends EvaluationCriterion<AggregateQuery> {
    private final Stages requiredStage;

    public StageCriterion(int priority, Stages requiredStage) {
        super(AggregateQuery.class, priority);
        this.requiredStage = requiredStage;
    }

    @Override
    protected void evaluate(AggregateQuery query) {

    }
}
