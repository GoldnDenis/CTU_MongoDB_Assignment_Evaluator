package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.aggregate;

import cz.cvut.fel.mongodb_assignment_evaluator.service.enums.CriterionDescription;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.MockMongoDB;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.AssignmentCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.DocumentParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.PipelineParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.QueryParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.query.Query;

import java.util.List;

public class AggregateFiveCriterion extends AssignmentCriterion {
    public AggregateFiveCriterion(MockMongoDB mockDb) {
        super(
                mockDb,
                CriterionDescription.AGGREGATE_FIVE.getDescription(),
                CriterionDescription.AGGREGATE_FIVE.getRequiredCount()
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
