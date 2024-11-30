package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.find.search;

import cz.cvut.fel.mongodb_assignment_evaluator.service.enums.CriterionDescription;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.MockMongoDB;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.AssignmentCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.QueryParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.StringParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.query.Query;
import org.bson.Document;

import java.util.*;

public class FindArrayCriterion extends FindFilterCriterion {
    public FindArrayCriterion(MockMongoDB mockDb) {
        super(
                mockDb,
                CriterionDescription.FIND_ARRAY.getDescription(),
                CriterionDescription.FIND_ARRAY.getRequiredCount(),
                Collections.emptySet()
        );
    }

    @Override
    protected boolean inspectFilter(Document filterDocument, int maxDepth) {
        Set<String> allArrayFields = mockDb.findAllFieldsOfType(collection, List.class);
        return inspectAllArrayFields(filterDocument, maxDepth, allArrayFields);
    }

    private boolean inspectAllArrayFields(Document filterDocument, int maxDepth, Set<String> allArrayFields) {
        for (Map.Entry<String, Object> entry : filterDocument.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            for (String arrayField : allArrayFields) {
                Object found = filterDocument.get(arrayField);
                if (found != null
                        && found instanceof List) {
                    return true;
                }
            }

            if (value instanceof Document) {
                if (inspectAllArrayFields((Document) value, maxDepth, allArrayFields)) {
                    return true;
                }
            } else if (value instanceof List<?> list) {
                for (Object item : list) {
                    if (item instanceof Document) {
                        if (inspectAllArrayFields((Document) item, maxDepth, allArrayFields)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}
