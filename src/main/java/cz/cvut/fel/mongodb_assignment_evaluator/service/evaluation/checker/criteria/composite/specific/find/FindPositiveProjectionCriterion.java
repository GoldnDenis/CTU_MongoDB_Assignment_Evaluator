package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.composite.specific.find;

import cz.cvut.fel.mongodb_assignment_evaluator.service.enums.Criteria;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.InsertedDocumentStorage;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.AssignmentCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.DocumentParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.QueryParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.query.type.Query;
import org.bson.BsonDocument;
import org.bson.BsonValue;

import java.util.List;

public class FindPositiveProjectionCriterion extends AssignmentCriterion {
    public FindPositiveProjectionCriterion(InsertedDocumentStorage mockDb) {
        super(
                mockDb,
                Criteria.FIND_POSITIVE_PROJECTION.getDescription(),
                Criteria.FIND_POSITIVE_PROJECTION.getRequiredCount()
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
                if ((value.isBoolean() && !value.asBoolean().getValue()) ||
                        (value.isInt32() && value.asInt32().getValue() == 0)) {
                    return;
                }
            }
            currentScore++;
            satisfied = true;
        }
        curParamIdx++;
    }
}
