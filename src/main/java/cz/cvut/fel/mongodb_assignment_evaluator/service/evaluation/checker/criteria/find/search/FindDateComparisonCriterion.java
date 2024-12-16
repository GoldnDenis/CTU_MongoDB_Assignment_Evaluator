package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.find.search;

import cz.cvut.fel.mongodb_assignment_evaluator.service.enums.Criteria;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.MockMongoDB;
import org.bson.BsonDocument;
import org.bson.BsonValue;

import java.util.*;

public class FindDateComparisonCriterion extends FindFilterCriterion {
    public FindDateComparisonCriterion(MockMongoDB mockDb) {
        super(
                mockDb,
                Criteria.FIND_DATE_COMPARISON.getDescription(),
                Criteria.FIND_DATE_COMPARISON.getRequiredCount(),
                Collections.emptySet()
        );
    }

    @Override
    protected boolean inspectFilter(BsonDocument filterDocument, int maxDepth) {
        Set<String> allDateFields = mockDb.findAllFieldsOfType(collection, Date.class);
        for (Map.Entry<String, BsonValue> entry : filterDocument.entrySet()) {
            String key = entry.getKey();
            BsonValue value = entry.getValue();

            if (allDateFields.contains(key)) {
                return true;
            }

            if (value.isDocument()) {
                if (inspectFilter(value.asDocument(), maxDepth)) {
                    return true;
                }
            } else if (value.isArray()) {
                for (BsonValue item : value.asArray()) {
                    if (item.isDocument()) {
                        if (inspectFilter(item.asDocument(), maxDepth)) {
                            return true;
                        }
                    } else if (item.isString()) {
                        if (allDateFields.contains(item.asString().getValue())) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}
