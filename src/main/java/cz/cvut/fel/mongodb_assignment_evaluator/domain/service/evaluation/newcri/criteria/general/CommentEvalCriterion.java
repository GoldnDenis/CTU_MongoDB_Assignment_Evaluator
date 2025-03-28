package cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.newcri.criteria.general;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type.Query;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.newcri.EvaluationCriterion;

public class CommentEvalCriterion extends EvaluationCriterion<Query> {
    public CommentEvalCriterion() {
        super(Query.class);
    }

    @Override
    protected void evaluate(Query query) {
        String comment = query.getComment();
        if (comment != null && !comment.isBlank()) {
            currentScore++;
        }
    }
}
