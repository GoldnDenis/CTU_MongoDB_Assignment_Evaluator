package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.update.upsert;

import cz.cvut.fel.mongodb_assignment_evaluator.service.enums.CriterionDescription;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.MockMongoDB;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.AssignmentCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.DocumentParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.QueryParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.query.Query;
import org.bson.BsonValue;

import java.util.List;

public class UpdateUpsertUsedCriterion extends AssignmentCriterion {
    public UpdateUpsertUsedCriterion(MockMongoDB mockDb) {
        super(
                mockDb,
                CriterionDescription.UPDATE_UPSERT_USED.getDescription(),
                CriterionDescription.UPDATE_UPSERT_USED.getRequiredCount()
        );
    }

    @Override
    public void concreteCheck(Query query) {
        currentParameterIdx = 2;
        List<QueryParameter> parameters = query.getParameters();
        if (parameters.size() >= 3) {
            parameters.get(currentParameterIdx).accept(this);
        }
    }

    @Override
    public void visitDocumentParameter(DocumentParameter parameter) {
        if (currentParameterIdx == 2) {
            BsonValue found = parameter.getValue("upsert", 1);
            if (found != null) {
                if ((found.isBoolean() && found.asBoolean().getValue()) ||
                        (found.isInt32() && found.asInt32().getValue() == 1)) {
                    currentCount++;
                    satisfied = true;
                }
            }
        }
        currentParameterIdx++;
    }
}
