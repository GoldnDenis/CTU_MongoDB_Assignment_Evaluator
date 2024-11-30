package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.collection;

import cz.cvut.fel.mongodb_assignment_evaluator.service.enums.CriterionDescription;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.MockMongoDB;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.AssignmentCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.QueryParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.StringParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.query.Query;

import java.util.ArrayList;
import java.util.List;

public class CreateOneCollectionCriterion extends AssignmentCriterion {
    public CreateOneCollectionCriterion(MockMongoDB mockDb) {
        super(
                mockDb,
                CriterionDescription.CREATE_ONE_COLLECTION.getDescription(),
                CriterionDescription.CREATE_ONE_COLLECTION.getRequiredCount()
        );
    }

    @Override
    public void concreteCheck(Query query) {
        currentParameterIdx = 0;
        List<QueryParameter> queryParameters = query.getParameters();
        if (!queryParameters.isEmpty()) {
            queryParameters.get(0).accept(this);
        }
    }

    @Override
    public void visitStringParameter(StringParameter parameter) {
        if (currentParameterIdx == 0) {
            if (mockDb.createCollection(parameter.getValue())) {
                currentCount++;
                satisfied = true;
            }
        }
        currentParameterIdx++;
    }
}
