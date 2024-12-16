package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.find.search;

import cz.cvut.fel.mongodb_assignment_evaluator.service.enums.Criteria;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.MockMongoDB;
import org.bson.BsonDocument;
import org.bson.BsonValue;

import java.util.Map;
import java.util.Set;

public class FindValueCriterion extends FindFilterCriterion {
    public FindValueCriterion(MockMongoDB mockDb) {
        super(
                mockDb,
                Criteria.FIND_VALUE.getDescription(),
                Criteria.FIND_VALUE.getRequiredCount(),
                Set.of("$eq", "$ne")
        );
    }

    @Override
    protected boolean inspectFilter(BsonDocument filterDocument, int maxDepth) {
        for (Map.Entry<String, BsonValue> entry : filterDocument.entrySet()) {
            String key = entry.getKey();
            BsonValue value = entry.getValue();
            if (key.startsWith("$")) {
                if (expectedOperators.contains(key)) {
                    return true;
                }
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
                    } else {
                        return true;
                    }
                }
            }
            return true;
        }
        return false;
    }
}
