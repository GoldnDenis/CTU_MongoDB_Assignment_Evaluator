package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.find.search;

import cz.cvut.fel.mongodb_assignment_evaluator.service.enums.CriterionDescription;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.MockMongoDB;
import org.bson.Document;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class FindValueCriterion extends FindFilterCriterion {
    public FindValueCriterion(MockMongoDB mockDb) {
        super(
                mockDb,
                CriterionDescription.FIND_VALUE.getDescription(),
                CriterionDescription.FIND_VALUE.getRequiredCount(),
                Set.of("$eq", "$ne")
        );
    }

    @Override
    protected boolean inspectFilter(Document filterDocument, int maxDepth) {
        for (Map.Entry<String, Object> entry : filterDocument.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (key.startsWith("$")) {
                if (expectedOperators.contains(key)) {
                    return true;
                }
            }
            if (value instanceof Document) {
                if (inspectFilter((Document) value, maxDepth)) {
                    return true;
                }
            } else if (value instanceof List<?>) {
                for (Object item : (List<?>) value) {
                    if (item instanceof Document) {
                        if (inspectFilter((Document) item, maxDepth)) {
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
