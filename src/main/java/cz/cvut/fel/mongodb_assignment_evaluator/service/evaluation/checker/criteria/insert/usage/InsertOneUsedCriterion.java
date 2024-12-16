package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.insert.usage;

import cz.cvut.fel.mongodb_assignment_evaluator.service.enums.Criteria;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.MockMongoDB;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.AssignmentCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.QueryParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.query.Query;

import java.util.List;

public class InsertOneUsedCriterion extends AssignmentCriterion {
    public InsertOneUsedCriterion(MockMongoDB mockDb) {
        super(
                mockDb,
                Criteria.INSERT_ONE_USED.getDescription(),
                Criteria.INSERT_ONE_USED.getRequiredCount()
        );
    }

    @Override
    public void concreteCheck(Query query) {
        if (!query.getOperator().equalsIgnoreCase("insertOne")) {
            return;
        }

        List<QueryParameter> parameters = query.getParameters();
        if (!parameters.isEmpty() && !parameters.get(0).isTrivial()) {
            satisfied = true;
            currentCount++;
        }
    }
}
