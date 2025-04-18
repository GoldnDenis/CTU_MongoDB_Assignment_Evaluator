package cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.criteria;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.enums.ResultStates;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type.Query;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class GradedCriteria {
    private final String name;
    private final String description;
    private final long requiredScore;
    private final long score;
    private final List<Query> fulfilledQueries;
    private final ResultStates resultState;

    public GradedCriteria(String name, String description, long requiredScore, long score, List<Query> fulfilledQueries) {
        this.name = name;
        this.description = description;
        this.requiredScore = requiredScore;
        this.score = score;
        this.fulfilledQueries = new ArrayList<>(fulfilledQueries);
        this.resultState = ResultStates.evaluate(score, requiredScore);
    }
}
