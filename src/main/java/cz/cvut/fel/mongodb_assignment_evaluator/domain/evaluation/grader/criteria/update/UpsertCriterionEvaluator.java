package cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.grader.criteria.update;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.grader.criteria.CriterionEvaluator;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.entity.Criterion;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type.UpdateQuery;

/**
 * Checks the options document for an upsert: true flag.
 */
public class UpsertCriterionEvaluator extends CriterionEvaluator<UpdateQuery> {
    public UpsertCriterionEvaluator(Criterion criterion) {
        super(UpdateQuery.class, criterion);
    }

    protected void evaluate(UpdateQuery query) {
        currentScore = query.containsUpsert() ? 1 : 0;
    }
}
