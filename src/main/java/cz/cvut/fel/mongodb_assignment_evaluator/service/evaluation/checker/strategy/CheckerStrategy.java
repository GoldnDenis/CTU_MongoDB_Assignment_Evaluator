package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.strategy;

import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.FeedbackCollector;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.InsertedDocumentStorage;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.AssignmentCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.EvaluationResult;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.query.type.Query;

import java.util.ArrayList;
import java.util.List;

public abstract class CheckerStrategy {
    protected final InsertedDocumentStorage mockDb;
    protected final List<AssignmentCriterion> criteria;

    public CheckerStrategy(InsertedDocumentStorage mockDb, List<AssignmentCriterion> criteria) {
        this.mockDb = mockDb;
        this.criteria = new ArrayList<>(criteria);
    }

    public void checkCriteria(Query query) {
        criteria.forEach(c -> c.check(query));
    }

    public void collectEvaluationResults(FeedbackCollector feedbackCollector) {
        criteria.forEach(c -> feedbackCollector.addFeedback(c.generateFeedback()));
    }

    public List<EvaluationResult> collectEvaluationResults() {
        return criteria.stream()
                .map(AssignmentCriterion::getEvaluation)
                .toList();
    }
}
