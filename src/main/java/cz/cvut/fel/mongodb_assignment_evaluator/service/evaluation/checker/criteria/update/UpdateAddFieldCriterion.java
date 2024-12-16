package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.update;

import cz.cvut.fel.mongodb_assignment_evaluator.service.enums.Criteria;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.MockMongoDB;

import java.util.List;

public class UpdateAddFieldCriterion extends UpdateCriterion {
    public UpdateAddFieldCriterion(MockMongoDB mockDb) {
        super(
                mockDb,
                Criteria.UPDATE_ADD_FIELD.getDescription(),
                Criteria.UPDATE_ADD_FIELD.getRequiredCount(),
                List.of("$set", "$mul", "$inc"),
                false
        );
    }
}
