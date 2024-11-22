package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker;

import org.bson.Document;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BsonChecker {
//    public static boolean containsArray(Document document) {
//        for (Object value : document.values()) {
//            if (value instanceof Document) {
//                if (containsArray((Document) value)) {
//                    return true;
//                }
//            } else if (value instanceof List) {
//                return true;
//            }
//        }
//        return false;
//    }

    public static String findFieldWithArray(Document document) {
        for (Map.Entry<String, Object> entry : document.entrySet()) {
            String key = entry.getKey();
            if (!key.startsWith("$") &&
                    entry.getValue() instanceof List) {
                return key;
            } else if (entry.getValue() instanceof Document) {
                String found = findFieldWithArray((Document) entry.getValue());
                if (!found.isBlank()) {
                    return found;
                }
            }
        }
        return "";
    }

//    public static boolean containsEmbeddedObject(Document document) {
//        for (Object value : document.values()) {
//            if (value instanceof Document) {
//                return true;
//            }
//        }
//        return false;
//    }

    public static String findFieldMatchesPattern(Document document, Pattern pattern) {
        for (Map.Entry<String, Object> entry : document.entrySet()) {
            String key = entry.getKey();
            if (pattern.matcher(key).matches()) {
                return key;
            }
        }
        return "";
    }

    public static String findFieldContainsSubstring(Document document, String substring) {
        for (Map.Entry<String, Object> entry : document.entrySet()) {
            String key = entry.getKey();
            if (key.contains(substring)) {
                return key;
            }
        }
        return "";
    }

    public static String findFieldWithEmbeddedObject(Document document) {
        for (Map.Entry<String, Object> entry : document.entrySet()) {
            String key = entry.getKey();
            if (!key.startsWith("$") &&
                    entry.getValue() instanceof Document) {
                return key;
            }
        }
        return "";
    }

    public static Document getFirstLevelOperatorDocument(Document document, String operator) {
        if (!operator.startsWith("$")) {
            return null;
        }
        List<Document> documents = document.entrySet().stream()
                .filter(entry -> entry.getKey().equalsIgnoreCase(operator))
                .filter(entry -> entry.getValue() instanceof Document)
                .map(entry -> (Document) entry.getValue())
                .toList();
        if (documents.isEmpty()) {
            return null;
        }
        return documents.get(0);
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
