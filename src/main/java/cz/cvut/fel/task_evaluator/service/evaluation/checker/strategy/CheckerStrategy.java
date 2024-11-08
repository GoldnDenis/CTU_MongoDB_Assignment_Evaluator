package cz.cvut.fel.task_evaluator.service.evaluation.checker.strategy;

import cz.cvut.fel.task_evaluator.service.evaluation.checker.criteria.AssignmentCriterion;
import cz.cvut.fel.task_evaluator.service.evaluation.query.Query;

import java.util.ArrayList;
import java.util.List;

public abstract class CheckerStrategy {
    protected final List<AssignmentCriterion> criteria;

    public CheckerStrategy() {
        this.criteria = new ArrayList<>();
    }

    public void checkCriteria(Query query) {
        for (AssignmentCriterion criterion : criteria) {
            criterion.check(query);
        }
    }

    public void collectAllFeedback() {
        for (AssignmentCriterion criterion : criteria) {
            criterion.generateFeedback();
        }
    }
}
