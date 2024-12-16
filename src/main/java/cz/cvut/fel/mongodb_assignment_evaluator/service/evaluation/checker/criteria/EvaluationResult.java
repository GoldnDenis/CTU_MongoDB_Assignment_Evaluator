package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria;

import cz.cvut.fel.mongodb_assignment_evaluator.service.enums.Criteria;
import cz.cvut.fel.mongodb_assignment_evaluator.service.enums.ResultStates;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.query.Query;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class EvaluationResult {
    private final Criteria criterion;
    private ResultStates state;
    private final List<Query> satisfiedQueries;
    private final List<Query> failedQueries;

    public EvaluationResult(Criteria criterion) {
        this.criterion = criterion;
        this.state = ResultStates.NOT_FULFILLED;
        this.satisfiedQueries = new ArrayList<>();
        this.failedQueries = new ArrayList<>();
    }

    public void logQuery(Query query, Boolean satisfied) {
        if (satisfied) {
            satisfiedQueries.add(query);
        } else {
            failedQueries.add(query);
        }
    }
}
