package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.strategy;

import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.FeedbackCollector;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.MockMongoDB;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.AssignmentCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.query.Query;

import java.util.ArrayList;
import java.util.List;

public abstract class CheckerStrategy {
    protected final MockMongoDB mockDb;
    protected final List<AssignmentCriterion> criteria;

    public CheckerStrategy(MockMongoDB mockDb, List<AssignmentCriterion> criteria) {
        this.mockDb = mockDb;
        this.criteria = new ArrayList<>(criteria);
    }

    public void checkCriteria(Query query) {
        criteria.forEach(c -> c.check(query));
    }

    public void collectEvaluationResults(FeedbackCollector feedbackCollector) {
        criteria.forEach(c -> feedbackCollector.addFeedback(c.generateFeedback()));
    }
}
