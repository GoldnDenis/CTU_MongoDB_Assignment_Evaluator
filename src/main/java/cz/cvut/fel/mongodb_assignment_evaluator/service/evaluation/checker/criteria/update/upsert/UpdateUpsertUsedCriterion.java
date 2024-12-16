package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.update.upsert;

import cz.cvut.fel.mongodb_assignment_evaluator.service.enums.Criteria;
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
                Criteria.UPDATE_UPSERT_USED.getDescription(),
                Criteria.UPDATE_UPSERT_USED.getRequiredCount()
        );
    }

    @Override
    public void concreteCheck(Query query) {
        curParamIdx = 2;
        List<QueryParameter> parameters = query.getParameters();
        if (parameters.size() >= 3) {
            parameters.get(curParamIdx).accept(this);
        }
    }

    @Override
    public void visitDocumentParameter(DocumentParameter parameter) {
        if (curParamIdx == 2) {
            BsonValue found = parameter.getValue("upsert", 1);
            if (found != null) {
                if ((found.isBoolean() && found.asBoolean().getValue()) ||
                        (found.isInt32() && found.asInt32().getValue() == 1)) {
                    currentCount++;
                    satisfied = true;
                }
            }
        }
        curParamIdx++;
    }
}
