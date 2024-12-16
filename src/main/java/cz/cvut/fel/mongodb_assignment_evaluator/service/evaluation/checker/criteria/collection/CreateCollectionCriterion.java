package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.collection;

import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.MockMongoDB;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.AssignmentCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.QueryParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.query.Query;

import java.util.List;

public class CreateCollectionCriterion extends AssignmentCriterion {

    public CreateCollectionCriterion(MockMongoDB mockDb, String assignmentMessage, int requiredCount) {
        super(mockDb, assignmentMessage, requiredCount);
    }

    @Override
    protected void concreteCheck(Query query) {
        checkParameters(query.getParameters());
    }
}
