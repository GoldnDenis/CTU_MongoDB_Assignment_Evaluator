package cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.grader.criteria.general;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.grader.criteria.EvaluationCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.entity.Criterion;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.evaluation.GradedCriteria;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type.QueryToken;

public class CommentEvalCriterion extends EvaluationCriterion<QueryToken> {
    private int minCount;

    public CommentEvalCriterion(Criterion criterion) {
        super(QueryToken.class, criterion);
        minCount = 0;
    }

    @Override
    protected void evaluate(QueryToken query) {
        minCount++;
        currentScore = query.isCommented() ? 1 : 0;
    }

    @Override
    public GradedCriteria getResult() {
        GradedCriteria result = new GradedCriteria(criterion, maxScore, fulfilledQueries);
        result.changeRequiredScore(minCount);
        maxScore = 0;
        minCount = 0;
        currentScore = 0;
        fulfilledQueries.clear();
        return result;
    }
}
