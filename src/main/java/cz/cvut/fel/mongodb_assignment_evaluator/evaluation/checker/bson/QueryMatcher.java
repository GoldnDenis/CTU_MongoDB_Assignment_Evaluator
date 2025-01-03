package cz.cvut.fel.mongodb_assignment_evaluator.evaluation.checker.bson;

import lombok.extern.java.Log;
import org.bson.BsonArray;
import org.bson.BsonDocument;
import org.bson.BsonValue;

import java.util.Map;

@Log
public class QueryMatcher {
    private final static BsonValueComparator comparator = new BsonValueComparator();

    public static boolean matches(BsonDocument document, BsonDocument query) {
        try {
            for (Map.Entry<String, BsonValue> entry : query.entrySet()) {
                String queryKey = entry.getKey();
                BsonValue queryValue = entry.getValue();
                if (queryKey.startsWith("$")) {
                    return evaluateCondition(queryKey, queryValue, document);
                }
                if (document.containsKey(queryKey)) {
                    if (evaluateFieldSelector(queryKey, queryValue, document.get(queryKey))) {
                        return true;
                    }
                }
            }
        } catch (UnsupportedOperationException e) {
            // todo
            log.severe(e.getMessage());
            return true;
        } catch (IllegalArgumentException e) {
            log.severe(e.getMessage());
            return false;
        }
        return false;
    }

    private static boolean evaluateCondition(String selector, BsonValue filterValue, BsonDocument document) {
        return switch (selector) {
            case "$and" -> evaluateAnd(filterValue, document);
            case "$or" -> evaluateOr(filterValue, document);
            case "$not" -> evaluateNot(filterValue, document);
            case "$nor" -> evaluateNor(filterValue, document);
            default -> throw new UnsupportedOperationException("Unsupported selector: " + selector);
        };
    }

    private static boolean evaluateFieldSelector(String selector, BsonValue filterValue, BsonValue documentValue) {
        if (!selector.startsWith("$")) {
            return filterValue.equals(documentValue);
        }
        return switch (selector) {
            case "$eq" -> comparator.compare(filterValue, documentValue) == 0;
            case "$lt" -> comparator.compare(filterValue, documentValue) < 0;
            case "$lte" -> comparator.compare(filterValue, documentValue) <= 0;
            case "$gt" -> comparator.compare(filterValue, documentValue) > 0;
            case "$gte" -> comparator.compare(filterValue, documentValue) >= 0;
            case "$ne" -> comparator.compare(filterValue, documentValue) != 0;
            case "$elemMatch" -> evaluateElemMatch(filterValue, documentValue);
            default -> throw new UnsupportedOperationException("Unsupported selector: " + selector);
        };
    }

    // todo :: extend on array selectors

    private static boolean evaluateElemMatch(BsonValue filterValue, BsonValue documentValue) {
        if (!filterValue.isDocument()) {
            throw new IllegalArgumentException("$elemMatch requires a query document");
        }
        if (!documentValue.isArray()) {
            throw new IllegalArgumentException("$elemMatch should match an array field");
        }
        BsonDocument query = filterValue.asDocument();
        BsonArray arrayValues = documentValue.asArray();
        if (arrayValues.isEmpty()) {
            return false;
        }
        for (BsonValue arrayValue: arrayValues) {
            for (Map.Entry<String, BsonValue> entry : query.entrySet()) {
                if (!evaluateFieldSelector(entry.getKey(), entry.getValue(), arrayValue)) {
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean evaluateAnd(BsonValue filterValue, BsonDocument document) {
        if (!filterValue.isArray()) {
            throw new IllegalArgumentException("$and requires an array");
        }
        if (filterValue.asArray().isEmpty()) {
            return false;
        }
        for (BsonValue condition : filterValue.asArray()) {
            if (!matches(condition.asDocument(), document)) {
                return false;
            }
        }
        return true;
    }

    private static boolean evaluateOr(BsonValue filterValue, BsonDocument document) {
        if (!filterValue.isArray()) {
            throw new IllegalArgumentException("$or requires an array");
        }
        for (BsonValue condition : filterValue.asArray()) {
            if (matches(condition.asDocument(), document)) {
                return true;
            }
        }
        return false;
    }

    private static boolean evaluateNot(BsonValue filterValue, BsonDocument document) {
        if (!filterValue.isDocument()) {
            throw new IllegalArgumentException("$not requires a document");
        }
        return !matches(filterValue.asDocument(), document);
    }

    private static boolean evaluateNor(BsonValue filterValue, BsonDocument document) {
        if (!filterValue.isArray()) {
            throw new IllegalArgumentException("$nor requires an array");
        }
        return !evaluateOr(filterValue, document);
    }
}
