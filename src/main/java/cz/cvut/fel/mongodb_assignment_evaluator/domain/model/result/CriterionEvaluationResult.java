package cz.cvut.fel.mongodb_assignment_evaluator.domain.model.result;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.enums.Criteria;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.enums.ResultStates;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type.Query;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class CriterionEvaluationResult {
    private final Criteria criterion;
    private final Map<Query, Integer> satisfiedQueryScoreMap;
    private final List<Query> failedQueries;
    private int score; // may not be necessary, could be calculated from the map
    @Setter
    private int criterionModifier;

    public CriterionEvaluationResult(Criteria criterion) {
        this.criterion = criterion;
        this.satisfiedQueryScoreMap = new HashMap<>();
        this.failedQueries = new ArrayList<>();
        this.score = 0;
        this.criterionModifier = 1;
    }

    public void saveQuery(Query query, int queryScore) {
        if (queryScore > 0) {
            score += queryScore; // could be replaced with a sumAll method
            if (satisfiedQueryScoreMap.containsKey(query)) {
                queryScore += satisfiedQueryScoreMap.get(query);
            }
            satisfiedQueryScoreMap.put(query, queryScore);
        } else {
            failedQueries.add(query);
        }
    }

    public int getRequiredCount() {
        return criterionModifier * criterion.getRequiredCount();
    }

    public ResultStates evaluateState() {
        return ResultStates.evaluate(score, getRequiredCount());
    }
}
