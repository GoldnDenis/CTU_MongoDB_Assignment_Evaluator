package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.find.search;

import cz.cvut.fel.mongodb_assignment_evaluator.service.enums.CriterionDescription;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.BsonChecker;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.MockMongoDB;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.AssignmentCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.QueryParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.StringParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.query.Query;
import org.bson.Document;

import java.util.*;

public class FindDateComparisonCriterion extends FindFilterCriterion {
    public FindDateComparisonCriterion(MockMongoDB mockDb) {
        super(
                mockDb,
                CriterionDescription.FIND_DATE_COMPARISON.getDescription(),
                CriterionDescription.FIND_DATE_COMPARISON.getRequiredCount(),
                Collections.emptySet()
        );
    }

    @Override
    protected boolean inspectFilter(Document filterDocument, int maxDepth) {
        Set<String> allDateFields = mockDb.findAllFieldsOfType(collection, Date.class);
        for (Map.Entry<String, Object> entry : filterDocument.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            if (allDateFields.contains(key)) {
                return true;
            }

            if (value instanceof Document) {
                if (inspectFilter((Document) value, maxDepth)) {
                    return true;
                }
            } else if (value instanceof List<?> list) {
                for (Object item : list) {
                    if (item instanceof Document) {
                        if (inspectFilter((Document) item, maxDepth)) {
                            return true;
                        }
                    } else if (item instanceof String) {
                        if (allDateFields.contains(item)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}
