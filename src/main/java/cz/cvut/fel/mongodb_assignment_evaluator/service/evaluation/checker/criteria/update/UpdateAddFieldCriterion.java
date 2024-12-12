package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.update;

import cz.cvut.fel.mongodb_assignment_evaluator.service.enums.CriterionDescription;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.MockMongoDB;
import org.bson.BsonDocument;
import org.bson.Document;

import java.util.List;
import java.util.Set;

public class UpdateAddFieldCriterion extends UpdateCriterion {
    public UpdateAddFieldCriterion(MockMongoDB mockDb) {
        super(
                mockDb,
                CriterionDescription.UPDATE_ADD_FIELD.getDescription(),
                CriterionDescription.UPDATE_ADD_FIELD.getRequiredCount(),
                List.of("$set", "$mul", "$inc"),
                false
        );
    }
}
