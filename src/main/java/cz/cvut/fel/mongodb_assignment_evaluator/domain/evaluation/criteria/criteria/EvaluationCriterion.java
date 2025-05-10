package cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.criteria.criteria;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.entity.Criterion;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.evaluation.GradedCriteria;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type.QueryToken;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
public abstract class EvaluationCriterion<Q extends QueryToken> {
    private final Class<Q> queryType;
    private final int priority;

    protected final Criterion criterion;
    protected int maxScore;
    protected final List<QueryToken> fulfilledQueries;
    protected int currentScore;

    public EvaluationCriterion(Class<Q> queryType, Criterion criterion, int priority) {
        this.queryType = queryType;
        this.criterion = criterion;
        this.priority = priority;
        this.maxScore = 0;
        this.fulfilledQueries = new ArrayList<>();
        this.currentScore = 0;
    }

    public void check(QueryToken query) {
        currentScore = 0;
        evaluate(queryType.cast(query));
        if (currentScore > 0) {
            maxScore += currentScore;
            fulfilledQueries.add(query);
            query.addCriterion(criterion);
        }
    }

    protected abstract void evaluate(Q query);

    public GradedCriteria getResult() {
        GradedCriteria result = new GradedCriteria(criterion, maxScore, fulfilledQueries);
        maxScore = 0;
        currentScore = 0;
        fulfilledQueries.clear();
        return result;
    }
}
