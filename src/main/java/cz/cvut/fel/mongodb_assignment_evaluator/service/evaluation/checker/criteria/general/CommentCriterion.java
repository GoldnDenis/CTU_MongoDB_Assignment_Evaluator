package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.general;

import cz.cvut.fel.mongodb_assignment_evaluator.service.enums.Criteria;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.MockMongoDB;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.AssignmentCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.query.Query;

public class CommentCriterion extends AssignmentCriterion {
    public CommentCriterion(MockMongoDB mockDb) {
        super(
                mockDb,
                Criteria.COMMENT.getDescription(),
                0
        );
    }

    @Override
    public void concreteCheck(Query query) {
        requiredCount++;
        if (!query.getComment().isBlank()) {
            satisfied = true;
            currentCount++;
        }
    }

    @Override
    protected String generateQueryRelatedFeedback() {
        return "";
    }
}
