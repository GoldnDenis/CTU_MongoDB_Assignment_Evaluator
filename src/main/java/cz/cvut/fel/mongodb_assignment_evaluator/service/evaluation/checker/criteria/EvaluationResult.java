package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria;

import cz.cvut.fel.mongodb_assignment_evaluator.service.enums.Criteria;
import cz.cvut.fel.mongodb_assignment_evaluator.service.enums.ResultStates;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.query.type.Query;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class EvaluationResult {
    private final Criteria criterion;
    private final Map<Query, Integer> satisfiedQueries;
    private final List<Query> failedQueries;

//    private ResultStates state;
    private int score; // may not be necessary, could be calculated from the map

    @Setter
    private int criterionModifier;

    public EvaluationResult(Criteria criterion) {
        this.criterion = criterion;
        this.satisfiedQueries = new HashMap<>();
        this.failedQueries = new ArrayList<>();
//        this.state = ResultStates.NOT_FULFILLED;
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

    public ResultStates evaluateState() {
        return ResultStates.evaluate(score, criterionModifier * criterion.getRequiredCount());
    }
}
