package cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.criteria.criteria;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.entity.Criterion;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.evaluation.GradedCriteria;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type.QueryToken;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public abstract class EvaluationCriterion<Q extends QueryToken> {
    private final Class<Q> queryType;
    private final int priority;

    protected long maxScore;
    protected final List<QueryToken> fulfilledQueries;
    protected long currentScore;

    public EvaluationCriterion(Class<Q> queryType, int priority) {
        this.queryType = queryType;
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
        }
    }

    protected abstract void evaluate(Q query);

    public GradedCriteria getResult(Criterion criterion) {
        GradedCriteria result = new GradedCriteria(criterion.getName(), criterion.getDescription(), criterion.getMinCount(), maxScore, fulfilledQueries);
        maxScore = 0;
        currentScore = 0;
        fulfilledQueries.clear();
        return result;
    }
}
