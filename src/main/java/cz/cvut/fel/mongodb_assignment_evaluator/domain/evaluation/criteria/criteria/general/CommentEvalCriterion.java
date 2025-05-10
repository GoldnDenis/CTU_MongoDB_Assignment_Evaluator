package cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.criteria.criteria.general;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.criteria.criteria.EvaluationCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.entity.Criterion;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.evaluation.GradedCriteria;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type.QueryToken;

public class CommentEvalCriterion extends EvaluationCriterion<QueryToken> {
    private int minCount;

    public CommentEvalCriterion(Criterion criterion, int priority) {
        super(QueryToken.class, criterion, priority);
        minCount = 0;
    }

    @Override
    protected void evaluate(QueryToken query) {
        minCount++;
        String comment = query.getComment();
        if (comment != null && !comment.isBlank()) {
            currentScore++;
        }
    }

    @Override
    public GradedCriteria getResult() {
        GradedCriteria result = new GradedCriteria(criterion, maxScore, fulfilledQueries);
        result.setRequiredScore(minCount);
        maxScore = 0;
        minCount = 0;
        currentScore = 0;
        fulfilledQueries.clear();
        return result;
    }
}
