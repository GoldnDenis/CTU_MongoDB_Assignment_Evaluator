package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.update.array;

import cz.cvut.fel.mongodb_assignment_evaluator.service.enums.CriterionDescription;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.MockMongoDB;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.update.UpdateCriterion;

import java.util.List;

//todo update
public class UpdateArrayRemoveCriterion extends UpdateCriterion {
    public UpdateArrayRemoveCriterion(MockMongoDB mockDb) {
        super(
                mockDb,
                CriterionDescription.UPDATE_ARRAY_REMOVE.getDescription(),
                CriterionDescription.UPDATE_ARRAY_REMOVE.getRequiredCount(),
                List.of("$pull", "$pop"),
                true
        );
    }
}
