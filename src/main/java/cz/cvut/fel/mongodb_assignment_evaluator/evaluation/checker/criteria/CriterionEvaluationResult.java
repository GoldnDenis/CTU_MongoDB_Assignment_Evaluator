package cz.cvut.fel.mongodb_assignment_evaluator.evaluation.checker.criteria;

import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.model.enums.Criteria;
import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.model.enums.ResultStates;
import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.model.query.type.Query;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class CriterionEvaluationResult {
    private final Criteria criterion;
    private final Map<Query, Integer> satisfiedQueries;
    private final List<Query> failedQueries;
    private int score; // may not be necessary, could be calculated from the map
    @Setter
    private int criterionModifier;

    public CriterionEvaluationResult(Criteria criterion) {
        this.criterion = criterion;
        this.satisfiedQueries = new HashMap<>();
        this.failedQueries = new ArrayList<>();
        this.score = 0;
        this.criterionModifier = 1;
    }

    public void log(Query query, int queryScore) {
        if (queryScore > 0) {
            score += queryScore; // could be replaced with a sumAll method
            if (satisfiedQueries.containsKey(query)) {
                queryScore += satisfiedQueries.get(query);
            }
            satisfiedQueries.put(query, queryScore);
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
