package cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.criteria.criteria.insert;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type.InsertQuery;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.criteria.EvaluationCriterion;

public class InsertQuantityCriterion extends EvaluationCriterion<InsertQuery> {
    public InsertQuantityCriterion() {
        super(InsertQuery.class, 1);
    }

    @Override
    protected void evaluate(InsertQuery query) {
        long nonTrivialCount = query.getInsertedDocuments().stream()
                .filter(d -> !d.isEmpty())
                .count();
        if (nonTrivialCount > 0) {
            currentScore += (int) nonTrivialCount;//todo
        }
    }
}
