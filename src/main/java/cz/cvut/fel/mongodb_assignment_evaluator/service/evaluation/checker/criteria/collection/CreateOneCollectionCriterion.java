package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.collection;

import cz.cvut.fel.mongodb_assignment_evaluator.service.enums.Criteria;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.MockMongoDB;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.StringParameter;

public class CreateOneCollectionCriterion extends CreateCollectionCriterion {
    public CreateOneCollectionCriterion(MockMongoDB mockDb) {
        super(
                mockDb,
                Criteria.CREATE_ONE_COLLECTION.getDescription(),
                Criteria.CREATE_ONE_COLLECTION.getRequiredCount()
        );
    }

    @Override
    public void visitStringParameter(StringParameter parameter) {
        if (curParamIdx == 0) {
            if (mockDb.createCollection(parameter.getValue())) {
                currentCount++;
            }
        }
    }
}
