package cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.grader.criteria;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.entity.Criterion;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.evaluation.GradedCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type.MongoQuery;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * Definition of the common criterion evaluator behavior
 * @param <Q> defined type of the query for the criterion
 */
@Getter
public abstract class CriterionEvaluator<Q extends MongoQuery> {
    protected final Class<Q> queryType;
    protected final Criterion criterion;
    protected final List<MongoQuery> fulfilledQueries;

    protected int maxScore;
    protected int currentScore;

    public CriterionEvaluator(Class<Q> queryType, Criterion criterion) {
        this.queryType = queryType;
        this.criterion = criterion;
        this.maxScore = 0;
        this.fulfilledQueries = new ArrayList<>();
        this.currentScore = 0;
    }

    /**
     * casts the query into required specific subclass and tracks the results
     * @param mongoQuery
     */
    public void check(MongoQuery mongoQuery) {
        currentScore = 0;
        evaluate(queryType.cast(mongoQuery));
        if (currentScore > 0) {
            maxScore += currentScore;
            fulfilledQueries.add(mongoQuery);
            mongoQuery.addCriterion(criterion);
        }
    }

    /**
     * Concrete evaluation logic
     * @param query
     */
    protected abstract void evaluate(Q query);

    /**
     * Finalizes evaluation results, resets the evaluator's progress trackers
     * @return an object that represents a concluded criterion result
     */
    public GradedCriterion getResult() {
        GradedCriterion result = new GradedCriterion(criterion, maxScore, fulfilledQueries);
        maxScore = 0;
        currentScore = 0;
        fulfilledQueries.clear();
        return result;
    }
}
