package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.aggregate;

import cz.cvut.fel.mongodb_assignment_evaluator.service.enums.Criteria;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.MockMongoDB;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.AssignmentCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.QueryParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.query.Query;

import java.util.List;

public class AggregateFiveCriterion extends AssignmentCriterion {
    public AggregateFiveCriterion(MockMongoDB mockDb) {
        super(
                mockDb,
                Criteria.AGGREGATE_FIVE.getDescription(),
                Criteria.AGGREGATE_FIVE.getRequiredCount()
        );
    }

    @Override
    public void concreteCheck(Query query) {
        List<QueryParameter> parameters = query.getParameters();
        if (!parameters.isEmpty()) {
            if (!parameters.get(0).isTrivial()) {
                satisfied = true;
                currentCount++;
            }
        }
    }
}
