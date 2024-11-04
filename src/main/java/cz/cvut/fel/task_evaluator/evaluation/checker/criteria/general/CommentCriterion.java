package cz.cvut.fel.task_evaluator.evaluation.checker.criteria.general;

import cz.cvut.fel.task_evaluator.enums.CriterionMessages;
import cz.cvut.fel.task_evaluator.evaluation.checker.criteria.Criterion;
import cz.cvut.fel.task_evaluator.model.query.Query;

import java.util.ArrayList;
import java.util.List;

public class CommentCriterion extends Criterion {
    private final List<Query> failedQueryList;

    public CommentCriterion() {
        super(CriterionMessages.COMMENT.getMessage());
        this.failedQueryList = new ArrayList<>();
    }

    @Override
    public void check(Query query) {
        if (query.getComment().isBlank()) {
            failedQueryList.add(query);
        }
        System.out.println("test");
    }

    @Override
    public boolean isFulfilled() {
        return failedQueryList.isEmpty();
    }
}
