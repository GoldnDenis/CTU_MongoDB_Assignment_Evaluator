package cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.grader.criteria.general;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.enums.MongoCommands;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.grader.criteria.CriterionEvaluator;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.entity.Criterion;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type.MongoQuery;

/**
 * Pseudo-criterion, just counts all unrecognised criteria by the system
 */
public class UnrecognizedCriterionEvaluator extends CriterionEvaluator<MongoQuery> {
    public UnrecognizedCriterionEvaluator(Criterion criterion) {
        super(MongoQuery.class, criterion);
    }

    @Override
    protected void evaluate(MongoQuery mongoQuery) {
        if (mongoQuery.getType().equals(MongoCommands.UNRECOGNIZED)) {
            currentScore++;
        }
    }
}
