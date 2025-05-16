package cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.grader.criteria;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.entity.Criterion;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.evaluation.GradedCriteria;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type.QueryToken;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public abstract class EvaluationCriterion<Q extends QueryToken> {
    protected final Class<Q> queryType;
    protected final Criterion criterion;
    protected final List<QueryToken> fulfilledQueries;

    protected int maxScore;
    protected int currentScore;

    public EvaluationCriterion(Class<Q> queryType, Criterion criterion) {
        this.queryType = queryType;
        this.criterion = criterion;
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
