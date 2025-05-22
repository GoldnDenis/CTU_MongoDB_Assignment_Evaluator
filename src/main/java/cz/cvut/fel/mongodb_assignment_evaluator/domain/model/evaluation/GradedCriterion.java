package cz.cvut.fel.mongodb_assignment_evaluator.domain.model.evaluation;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.enums.ResultStates;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.entity.Criterion;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type.MongoQuery;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * Finalized criterion result with a computed fulfillment state, queries and scores
 */
@Getter
public class GradedCriterion {
    private final Criterion criterion;
    private final int score;
    private final List<MongoQuery> fulfilledQueries;
    private ResultStates resultState;
    private long requiredScore;

    public GradedCriterion(Criterion criterion, int score, List<MongoQuery> fulfilledQueries) {
        this.criterion = criterion;
        this.requiredScore = criterion.getMinCount();
        this.score = score;
        this.fulfilledQueries = new ArrayList<>(fulfilledQueries);
        this.resultState = ResultStates.evaluate(score, requiredScore);
    }

    /**
     * A method designed for score systems with dynamic score requirements
     * @param requiredScore new minimal bound
     */
    public void changeRequiredScore(long requiredScore) {
        this.requiredScore = requiredScore;
        this.resultState = ResultStates.evaluate(score, requiredScore);
    }

    public String getName() {
        return criterion.getName();
    }

    public String getDescription() {
        return criterion.getDescription();
    }
}
