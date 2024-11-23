package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker;

import org.bson.Document;

import java.util.Map;
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


    //    public static boolean containsEmbeddedObject(Document document) {
//        for (Object value : document.values()) {
//            if (value instanceof Document) {
//                return true;
//            }
//        }
//        return false;
//    }

    public static String findFieldValueOfType(Document document, int maxDepth, Class<?> clazz) {
        if (maxDepth <= 0) {
            return "";
        }

        for (Map.Entry<String, Object> entry : document.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (!key.startsWith("$") &&
                    clazz.isInstance(value)) {
                return key;
            } else if (maxDepth > 1 && value instanceof Document) {
                String found = findFieldValueOfType((Document) value, maxDepth - 1, clazz);
                if (!found.isBlank()) {
                    return found;
                }
            }
        }
        return "";
    }

    public static String findFieldMatchesPattern(Document document, int maxDepth, Pattern pattern) {
        if (pattern == null || maxDepth <= 0) {
            return "";
        }

        for (Map.Entry<String, Object> entry : document.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (pattern.matcher(key).find()) {
                return key;
            } else if (maxDepth > 1 && value instanceof Document) {
                key = findFieldMatchesPattern((Document) value, maxDepth - 1, pattern);
                if (!key.isBlank()) {
                    return key;
                }
            }
        }
        return "";
    }

    public static Object getValue(Document document, int maxDepth, String field) {
        if (field.isBlank() || maxDepth <= 0) {
            return null;
        }

        if (document.containsKey(field)) {
            return document.get(field);
        }

        for (Object value : document.values()) {
            if (maxDepth > 1 && value instanceof Document) {
                value = getValue((Document) value, maxDepth - 1, field);
                if (value != null) {
                    return value;
                }
            }
        }

        return null;
    }
}
