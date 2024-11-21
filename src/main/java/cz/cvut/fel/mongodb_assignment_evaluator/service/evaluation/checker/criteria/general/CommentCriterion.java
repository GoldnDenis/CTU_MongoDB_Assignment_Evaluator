package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.general;

import cz.cvut.fel.mongodb_assignment_evaluator.service.enums.CriterionDescription;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.AssignmentCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.query.Query;

public class CommentCriterion extends AssignmentCriterion {
    public CommentCriterion() {
        //todo
        super(CriterionDescription.COMMENT.getDescription(), 1);
    }

    @Override
    public void concreteCheck(Query query) {
        requiredCount++;
        if (!query.getComment().isBlank()) {
            satisfied = true;
            currentCount++;
        }
    }

//    @Override
//    public boolean isFulfilled() {
//        return failedQueryList.isEmpty();
//    }
//
//    @Override
//    protected void generatePositiveFeedback() {
//        System.out.println("All queries are properly commented.");
//    }
//
//    @Override
//    protected void generateNegativeFeedback() {
//        for (Query query : failedQueryList) {
//            System.out.println("\t" + query.toString() + " missing a comment.");
//        }
//    }
}
