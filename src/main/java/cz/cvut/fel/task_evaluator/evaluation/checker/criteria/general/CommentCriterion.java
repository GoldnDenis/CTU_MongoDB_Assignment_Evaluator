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
    }

    @Override
    public void generateFeedback() {

    }

    @Override
    public boolean isFulfilled() {
        for (Query query : failedQueryList) {
//            String position = query.getLineNumber() + ":" + query.getColumnNumber();
//            System.err.println("Query at " + position + " missing a comment");
            System.err.println(query.toString() + " missing a comment.");
        }
        return failedQueryList.isEmpty();
    }
}
