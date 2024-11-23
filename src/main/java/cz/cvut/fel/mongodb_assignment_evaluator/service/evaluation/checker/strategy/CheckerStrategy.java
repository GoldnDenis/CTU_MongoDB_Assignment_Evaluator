package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.strategy;

import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.FeedbackCollector;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.MockMongoDB;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.AssignmentCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.query.Query;

import java.util.ArrayList;
import java.util.List;

public abstract class CheckerStrategy {
    private final MockMongoDB mockDb;

    protected final List<AssignmentCriterion> criteria;

    public CheckerStrategy(MockMongoDB mockDb) {
        this.mockDb = mockDb;
        this.criteria = new ArrayList<>();
    }

    public void checkCriteria(Query query) {
        for (AssignmentCriterion criterion : criteria) {
            criterion.check(query);
        }
    }

    public void collectAllFeedback(FeedbackCollector feedbackCollector) {
        criteria.forEach(criterion -> feedbackCollector.addFeedback(criterion.generateFeedback()));
    }
}
