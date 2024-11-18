package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker;

import org.bson.Document;

import java.util.List;

public class BsonChecker {
    public static boolean containsArray(Document document) {
        for (Object value : document.values()) {
            if (value instanceof Document) {
                if (containsArray((Document) value)) {
                    return true;
                }
            } else if (value instanceof List) {
                return true;
            }
        }
        return false;
    }

    public static boolean containsEmbeddedObjects(Document document) {
        for (Object value : document.values()) {
            if (value instanceof Document) {
                return true;
            }
        }
        return false;
    }

    public static boolean contains(Document document, String fieldName) {
        if (document.containsKey(fieldName)) {
            return true;
        }
        for (Object value : document.values()) {
            if (value instanceof Document) {
                if (contains((Document) value, fieldName)) {
                    return true;
                }
            }
        }
        return false;
    }
}
