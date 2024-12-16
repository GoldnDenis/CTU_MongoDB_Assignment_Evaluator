package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.update.array;

import cz.cvut.fel.mongodb_assignment_evaluator.service.enums.Criteria;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.MockMongoDB;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.update.UpdateCriterion;

import java.util.List;

//todo update
public class UpdateArrayAddCriterion extends UpdateCriterion {
    public UpdateArrayAddCriterion(MockMongoDB mockDb) {
        super(
                mockDb,
                Criteria.UPDATE_ARRAY_ADD.getDescription(),
                Criteria.UPDATE_ARRAY_ADD.getRequiredCount(),
                List.of("$push"),
                true
        );
    }
}
