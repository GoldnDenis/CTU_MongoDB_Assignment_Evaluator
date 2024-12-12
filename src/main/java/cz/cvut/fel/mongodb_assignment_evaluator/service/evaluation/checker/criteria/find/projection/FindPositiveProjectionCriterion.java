package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.find.projection;

import cz.cvut.fel.mongodb_assignment_evaluator.service.enums.CriterionDescription;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.MockMongoDB;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.AssignmentCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.DocumentParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.QueryParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.StringParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.query.Query;
import org.bson.BsonDocument;
import org.bson.BsonValue;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class FindPositiveProjectionCriterion extends AssignmentCriterion {
    public FindPositiveProjectionCriterion(MockMongoDB mockDb) {
        super(
                mockDb,
                CriterionDescription.FIND_POSITIVE_PROJECTION.getDescription(),
                CriterionDescription.FIND_POSITIVE_PROJECTION.getRequiredCount()
        );
    }

    @Override
    public void concreteCheck(Query query) {
        currentParameterIdx = 1;
        List<QueryParameter> parameters = query.getParameters();
        if (parameters.size() >= 2) {
            parameters.get(1).accept(this);
        }
    }

    @Override
    public void visitDocumentParameter(DocumentParameter parameter) {
        if (currentParameterIdx == 1) {
            BsonDocument document = parameter.getDocument();
            for (String key : document.keySet()) {
                if (key.equals("_id")) {
                    continue;
                }

                BsonValue value = document.get(key);
                if (value == null) {
                    return;
                }
                if ((value.isBoolean() && !value.asBoolean().getValue()) ||
                        (value.isInt32() && value.asInt32().getValue() == 0)) {
                    return;
                }
            }
            currentCount++;
            satisfied = true;
        }
        currentParameterIdx++;
    }
}
