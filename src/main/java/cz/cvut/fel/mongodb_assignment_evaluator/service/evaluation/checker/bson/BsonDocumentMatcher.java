package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.bson;

import org.bson.BsonDocument;
import org.bson.BsonValue;

import java.util.Set;

public class BsonDocumentMatcher {
    public boolean matches(BsonDocument filter, BsonDocument document) {
        if (filter.isEmpty()) {
            return false;
        }
        for (String key : filter.keySet()) {
            if (!evaluateCondition(key, filter.get(key), document)) {
                return false;
            }
        }
        return true;
    }

    private boolean evaluateCondition(String selector, BsonValue filterValue, BsonDocument document) {
        switch (selector) {
            case "$and":
                return evaluateAnd(filterValue, document);
            case "$or":
                return evaluateOr(filterValue, document);
            case "$not":
                return evaluateNot(filterValue, document);
            case "$nor":
                return evaluateNor(filterValue, document);
            default:
                return evaluateFieldCondition(selector, filterValue, document);
        }
    }

    private boolean evaluateFieldCondition(String field, BsonValue filterValue, BsonDocument document) {
        BsonValue documentValue = document.get(field);
        if (filterValue.isDocument()) {
            BsonDocument condition = filterValue.asDocument();
            Set<String> operators = condition.keySet();
            for (String operator : operators) {
                if (!evaluateSelector(operator, condition.get(operator), documentValue)) {
                    return false;
                }
            }
            return true;
        } else {
            // Implicit `$eq` for non-operator values
            return evaluateSelector("$eq", filterValue, documentValue);
        }
    }

    private boolean evaluateSelector(String selector, BsonValue filterValue, BsonValue documentValue) {
        BsonValueComparator comparator = new BsonValueComparator();
        return switch (selector) {
            case "$eq" -> comparator.compare(filterValue, documentValue) == 0;
            case "$lt" -> comparator.compare(filterValue, documentValue) < 0;
            case "$lte" -> comparator.compare(filterValue, documentValue) <= 0;
            case "$gt" -> comparator.compare(filterValue, documentValue) > 0;
            case "$gte" -> comparator.compare(filterValue, documentValue) >= 0;
            case "$ne" -> comparator.compare(filterValue, documentValue) != 0;
            default -> throw new UnsupportedOperationException("Unsupported selector: " + selector);
        };
    }

    private boolean evaluateAnd(BsonValue filterValue, BsonDocument document) {
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

    private boolean evaluateOr(BsonValue filterValue, BsonDocument document) {
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

    private boolean evaluateNot(BsonValue filterValue, BsonDocument document) {
        if (!filterValue.isDocument()) {
            throw new IllegalArgumentException("$not requires a document");
        }
        return !matches(filterValue.asDocument(), document);
    }

    private boolean evaluateNor(BsonValue filterValue, BsonDocument document) {
        if (!filterValue.isArray()) {
            throw new IllegalArgumentException("$nor requires an array");
        }
        return !evaluateOr(filterValue, document);
    }
}
