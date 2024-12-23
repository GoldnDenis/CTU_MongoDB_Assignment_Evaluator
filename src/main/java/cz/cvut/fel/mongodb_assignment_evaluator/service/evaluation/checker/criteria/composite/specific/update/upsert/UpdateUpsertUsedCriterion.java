package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.composite.specific.update.upsert;

import cz.cvut.fel.mongodb_assignment_evaluator.service.enums.Criteria;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.InsertedDocumentStorage;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.AssignmentCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.DocumentParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.QueryParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.query.type.Query;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.query.type.UpdateQuery;
import org.bson.BsonValue;

import java.util.List;

public class UpdateUpsertUsedCriterion extends AssignmentCriterion<UpdateQuery> {
    public UpdateUpsertUsedCriterion(InsertedDocumentStorage documentStorage) {
        super(Criteria.UPDATE_UPSERT_USED, UpdateQuery.class, documentStorage);
    }

    @Override
    public void concreteCheck(UpdateQuery query) {
        BsonValue upsertValue = query.getOptions().getValue("upsert", 1);
        if (upsertValue != null) {
            if ((upsertValue.isBoolean() && upsertValue.asBoolean().getValue()) ||
                    (upsertValue.isInt32() && upsertValue.asInt32().getValue() == 1)) {
                currentScore++;
            }
        }
    }
}
