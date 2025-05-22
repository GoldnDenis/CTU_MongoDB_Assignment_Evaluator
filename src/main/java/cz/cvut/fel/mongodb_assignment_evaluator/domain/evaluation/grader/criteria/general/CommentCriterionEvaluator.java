package cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.grader.criteria.general;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.grader.criteria.CriterionEvaluator;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.entity.Criterion;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.evaluation.GradedCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type.MongoQuery;

/**
 * Counts the number of commented queries, comparing against the total number of extracted queries.
 */
public class CommentCriterionEvaluator extends CriterionEvaluator<MongoQuery> {
    private int minCount;

    public CommentCriterionEvaluator(Criterion criterion) {
        super(MongoQuery.class, criterion);
        minCount = 0;
    }

    @Override
    protected void evaluate(MongoQuery mongoQuery) {
        minCount++;
        currentScore = mongoQuery.isCommented() ? 1 : 0;
    }

    /**
     * Its required count depends on the total number of extracted queries
     */
    @Override
    public GradedCriterion getResult() {
        GradedCriterion result = new GradedCriterion(criterion, maxScore, fulfilledQueries);
        result.changeRequiredScore(minCount);
        maxScore = 0;
        minCount = 0;
        currentScore = 0;
        fulfilledQueries.clear();
        return result;
    }
}
