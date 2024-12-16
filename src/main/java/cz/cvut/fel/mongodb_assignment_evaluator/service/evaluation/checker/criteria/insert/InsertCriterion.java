package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.insert;

import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.MockMongoDB;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.AssignmentCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.query.Query;

public class InsertCriterion extends AssignmentCriterion {
    protected String currentCollection;

    public InsertCriterion(MockMongoDB mockDb, String assignmentMessage, int requiredCount) {
        super(mockDb, assignmentMessage, requiredCount);
        this.currentCollection = "";
    }

    @Override
    protected void concreteCheck(Query query) {
        currentCollection = query.getCollection();
        checkParameters(query.getParameters());
    }
}
