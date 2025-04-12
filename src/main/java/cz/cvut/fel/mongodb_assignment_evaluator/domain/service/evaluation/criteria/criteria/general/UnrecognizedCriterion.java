package cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.criteria.criteria.general;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.enums.Operators;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type.Query;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.criteria.EvaluationCriterion;

public class UnrecognizedCriterion extends EvaluationCriterion<Query> {
    public UnrecognizedCriterion(int priority) {
        super(Query.class, priority);
    }

    @Override
    protected void evaluate(Query query) {
        if (query.getType().equals(Operators.UNRECOGNIZED)) {
            currentScore++;
        }
    }
}
