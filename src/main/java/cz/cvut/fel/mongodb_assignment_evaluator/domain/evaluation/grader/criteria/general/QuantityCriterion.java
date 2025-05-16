package cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.grader.criteria.general;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.grader.criteria.EvaluationCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.entity.Criterion;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.evaluation.GradedCriteria;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type.InsertQueryToken;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type.QueryToken;

public class QuantityCriterion<Q extends QueryToken> extends EvaluationCriterion<Q> {
    public QuantityCriterion(Class<Q> queryType, Criterion criterion) {
        super(queryType, criterion);
    }

    @Override
    protected void evaluate(Q query) {
        currentScore = query.getQueryResultCount();
    }

    @Override
    public GradedCriteria getResult() {
        GradedCriteria result = new GradedCriteria(criterion, maxScore, fulfilledQueries);
        if (queryType.equals(InsertQueryToken.class)) {
            long collectionCount = fulfilledQueries.stream()
                    .map(QueryToken::getCollection)
                    .distinct()
                    .count();
            long minCount = collectionCount > 0 ? collectionCount * criterion.getMinCount() : criterion.getMinCount();
            result.changeRequiredScore(minCount);
        }
        maxScore = 0;
        currentScore = 0;
        fulfilledQueries.clear();
        return result;
    }
}
