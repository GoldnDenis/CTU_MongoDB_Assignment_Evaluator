package cz.cvut.fel.task_evaluator.evaluation.checker.strategy;

import cz.cvut.fel.task_evaluator.evaluation.checker.criteria.Criterion;
import cz.cvut.fel.task_evaluator.evaluation.checker.criteria.general.CommentCriterion;
import cz.cvut.fel.task_evaluator.model.query.Query;

import java.util.ArrayList;
import java.util.List;

public class GeneralStrategy {
    protected final List<Criterion> criteria;

    public GeneralStrategy() {
        this.criteria = new ArrayList<>();
        criteria.add(new CommentCriterion());

    }

    public void checkCriteria(Query query) {
        for (Criterion criterion : criteria) {
            criterion.check(query);
        }
    }
}
