package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.find.search;

import cz.cvut.fel.mongodb_assignment_evaluator.service.enums.CriterionDescription;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.MockMongoDB;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.AssignmentCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.QueryParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.StringParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.query.Query;
import org.bson.BsonDocument;
import org.bson.BsonValue;

import java.util.*;

public class FindArrayValueCriterion extends FindFilterCriterion {
    public FindArrayValueCriterion(MockMongoDB mockDb) {
        super(
                mockDb,
                CriterionDescription.FIND_ARRAY_VALUE.getDescription(),
                CriterionDescription.FIND_ARRAY_VALUE.getRequiredCount(),
                Set.of("$eq", "$in", "$all", "$elemMatch")
        );
    }

    @Override
    protected boolean inspectFilter(BsonDocument filterDocument, int maxDepth) {
        Set<String> allArrayFields = mockDb.findAllFieldsOfType(collection, List.class);
        return inspectAllArrayFields(filterDocument, maxDepth, allArrayFields);
    }

    private boolean inspectAllArrayFields(BsonDocument filterDocument, int maxDepth, Set<String> allArrayFields) {
        for (Map.Entry<String, BsonValue> entry : filterDocument.entrySet()) {
            String key = entry.getKey();
            BsonValue value = entry.getValue();

            for (String arrayField : allArrayFields) {
                BsonValue found = filterDocument.get(arrayField);
                if (found != null
                        && !found.isArray()) {
                    return true;
                }
            }

            if (value.isDocument()) {
                if (inspectAllArrayFields(value.asDocument(), maxDepth, allArrayFields)) {
                    return true;
                }
            } else if (value.isArray()) {
                for (BsonValue item : value.asArray()) {
                    if (item.isDocument()) {
                        if (inspectAllArrayFields(item.asDocument(), maxDepth, allArrayFields)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}
