package cz.cvut.fel.mongodb_assignment_evaluator.domain.model.evaluation;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.enums.ResultStates;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.entity.Criterion;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type.QueryToken;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
public class GradedCriteria {
    private final Criterion criterion;
    private final int score;
    private final List<QueryToken> fulfilledQueries;
    private final ResultStates resultState;
    @Setter
    private long requiredScore;

    public GradedCriteria(Criterion criterion, int score, List<QueryToken> fulfilledQueries) {
        this.criterion = criterion;
        this.requiredScore = criterion.getMinCount();
        this.score = score;
        this.fulfilledQueries = new ArrayList<>(fulfilledQueries);
        this.resultState = ResultStates.evaluate(score, requiredScore);
    }

    public String getName() {
        return criterion.getName();
    }

    public String getDescription() {
        return criterion.getDescription();
    }
}
