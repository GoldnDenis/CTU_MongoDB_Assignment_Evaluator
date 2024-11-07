package cz.cvut.fel.task_evaluator.evaluation.checker.strategy;

import cz.cvut.fel.task_evaluator.evaluation.checker.criteria.Criterion;
import cz.cvut.fel.task_evaluator.model.query.Query;

import java.util.ArrayList;
import java.util.List;

public abstract class CheckerStrategy {
    protected final List<Criterion> criteria;

    public CheckerStrategy() {
        this.criteria = new ArrayList<>();
    }

    public void checkCriteria(Query query) {
        for (Criterion criterion : criteria) {
            criterion.check(query);
        }
    }

    public void collectAllFeedback() {
        for (Criterion criterion : criteria) {
            criterion.generateFeedback();
        }
    }
}
