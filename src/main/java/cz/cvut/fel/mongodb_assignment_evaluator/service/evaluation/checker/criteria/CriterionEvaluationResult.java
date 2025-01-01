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

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        ResultStates resultState = evaluateState();
        string.append("'")
                .append(criterion.getDescription())
                .append("' ")
                .append(resultState.getText());
        if (criterion != Criteria.UNKNOWN_UNRECOGNISED) {
            string.append(" - ")
                    .append(score)
                    .append("/")
                    .append(criterionModifier * criterion.getRequiredCount());
        }
        if (resultState != ResultStates.NOT_FULFILLED &&
                criterion != Criteria.COMMENT) {
            string.append(":")
                    .append("\n")
                    .append(formatQueryList(satisfiedQueries.keySet().stream().toList()));
        } else {
            string.append(".")
                    .append("\n");
        }
        return string.toString();
    }

    private static String formatQueryList(List<Query> queryList) {
        StringBuilder string = new StringBuilder();
        for (int i = 0; i < queryList.size(); i++) {
            string.append("\t")
                    .append(i + 1)
                    .append(") ")
                    .append(queryList.get(i).getQuery())
                    .append("\n");
        }
        return string.toString();
    }
}
