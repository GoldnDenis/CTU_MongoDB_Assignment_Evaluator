package cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.grader.criteria.general;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.grader.criteria.CriterionEvaluator;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.entity.Criterion;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.evaluation.GradedCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type.InsertQuery;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type.MongoQuery;

/**
 * Counts non-trivial query operations.
 * The amount and triviality depends on the query type
 */
public class QuantityCriterionEvaluator<Q extends MongoQuery> extends CriterionEvaluator<Q> {
    public QuantityCriterionEvaluator(Class<Q> queryType, Criterion criterion) {
        super(queryType, criterion);
    }

    @Override
    protected void evaluate(Q query) {
        currentScore = query.getActionAmount();
    }

    /**
     * Insert has a dynamic score, relatively the number of collections
     */
    @Override
    public GradedCriterion getResult() {
        GradedCriterion result = new GradedCriterion(criterion, maxScore, fulfilledQueries);
        if (queryType.equals(InsertQuery.class)) {
            long collectionCount = fulfilledQueries.stream()
                    .map(MongoQuery::getCollection)
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
