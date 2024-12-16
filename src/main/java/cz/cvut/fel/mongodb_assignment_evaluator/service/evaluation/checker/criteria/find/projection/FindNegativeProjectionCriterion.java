package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.find.projection;

import cz.cvut.fel.mongodb_assignment_evaluator.service.enums.Criteria;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.MockMongoDB;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.AssignmentCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.DocumentParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.QueryParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.query.Query;
import org.bson.BsonDocument;
import org.bson.BsonValue;

import java.util.List;

public class FindNegativeProjectionCriterion extends AssignmentCriterion {
    public FindNegativeProjectionCriterion(MockMongoDB mockDb) {
        super(
                mockDb,
                Criteria.FIND_NEGATIVE_PROJECTION.getDescription(),
                Criteria.FIND_NEGATIVE_PROJECTION.getRequiredCount()
        );
    }

    @Override
    public void concreteCheck(Query query) {
        curParamIdx = 1;
        List<QueryParameter> parameters = query.getParameters();
        if (parameters.size() >= 2) {
            parameters.get(1).accept(this);
        }
    }

    @Override
    public void visitDocumentParameter(DocumentParameter parameter) {
        if (curParamIdx == 1) {
            BsonDocument document = parameter.getDocument();
            for (String key : document.keySet()) {
                if (key.equals("_id")) {
                    continue;
                }

                BsonValue value = document.get(key);
                if (value == null) {
                    return;
                }
                if ((value.isBoolean() && value.asBoolean().getValue()) ||
                        (value.isInt32() && value.asInt32().getValue() == 1)) {
                    return;
                }
            }
            currentCount++;
            satisfied = true;
        }
        curParamIdx++;
    }
}
