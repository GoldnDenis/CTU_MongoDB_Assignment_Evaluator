package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.replace;

import cz.cvut.fel.mongodb_assignment_evaluator.service.enums.Criteria;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.MockMongoDB;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.AssignmentCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.QueryParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.query.Query;

import java.util.List;

public class ReplaceValueCriterion extends AssignmentCriterion {
    public ReplaceValueCriterion(MockMongoDB mockDb) {
        super(
                mockDb,
                Criteria.REPLACE_VALUE.getDescription(),
                Criteria.REPLACE_VALUE.getRequiredCount()
        );
    }

    @Override
    public void concreteCheck(Query query) {
        List<QueryParameter> queryParameters = query.getParameters();
        if (query.getParameters().size() >= 2) {
            if (!queryParameters.get(1).isTrivial()) {
                currentCount++;
                satisfied = true;
            }
        }
    }

}
