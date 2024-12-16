package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.find.search;

import cz.cvut.fel.mongodb_assignment_evaluator.service.enums.Criteria;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.MockMongoDB;
import org.bson.BsonDocument;
import org.bson.BsonValue;

import java.util.*;

public class FindArrayCriterion extends FindFilterCriterion {
    public FindArrayCriterion(MockMongoDB mockDb) {
        super(
                mockDb,
                Criteria.FIND_ARRAY.getDescription(),
                Criteria.FIND_ARRAY.getRequiredCount(),
                Collections.emptySet()
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
                        && found.isArray()) {
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
