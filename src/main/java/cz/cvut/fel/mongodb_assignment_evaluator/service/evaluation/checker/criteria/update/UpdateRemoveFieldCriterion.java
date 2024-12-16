package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.update;

import cz.cvut.fel.mongodb_assignment_evaluator.service.enums.Criteria;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.MockMongoDB;

import java.util.List;

public class UpdateRemoveFieldCriterion extends UpdateCriterion {
    public UpdateRemoveFieldCriterion(MockMongoDB mockDb) {
        super(
                mockDb,
                Criteria.UPDATE_REMOVE_FIELD.getDescription(),
                Criteria.UPDATE_REMOVE_FIELD.getRequiredCount(),
                List.of("$unset"),
                true
        );
    }
}
