package cz.cvut.fel.task_evaluator.service.evaluation.checker.criteria.general;

import cz.cvut.fel.task_evaluator.service.enums.CriterionMessages;
import cz.cvut.fel.task_evaluator.service.evaluation.checker.criteria.AssignmentCriterion;
import cz.cvut.fel.task_evaluator.service.evaluation.query.Query;

import java.util.ArrayList;
import java.util.List;

public class CommentCriterion extends AssignmentCriterion {
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
    public boolean isFulfilled() {
        return failedQueryList.isEmpty();
    }

    @Override
    protected void generatePositiveFeedback() {
        System.out.println("All queries are properly commented.");
    }

    @Override
    protected void generateNegativeFeedback() {
        for (Query query : failedQueryList) {
            System.out.println("\t" + query.toString() + " missing a comment.");
        }
    }
}
