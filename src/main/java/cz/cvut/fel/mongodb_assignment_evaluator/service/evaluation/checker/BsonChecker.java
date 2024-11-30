package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker;

import org.bson.Document;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class BsonChecker {
    public static Set<String> findAllFieldsOfType(Document document, Class<?> clazz) {
        Set<String> fields = new HashSet<>();
        for (Map.Entry<String, Object> entry : document.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (!key.startsWith("$") &&
                    clazz.isInstance(value)) {
                fields.add(key);
            } else if (value instanceof Document) {
                fields.addAll(findAllFieldsOfType((Document) value, clazz));
            } else if (value instanceof List<?> valueList) {
                for (Object item : valueList) {
                    if (!key.startsWith("$") &&
                            clazz.isInstance(item)) {
                        fields.add(key);
                        break;
                    } else if (item instanceof Document) {
                        fields.addAll(findAllFieldsOfType((Document) item, clazz));
                    } else {
                        break;
                    }
                }
            }
        }
        return fields;
    }

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
            } else if (value instanceof List<?> valueList) {
                for (Object item : valueList) {
                    if (clazz.isInstance(item)) {
                        return key;
                    } else if (item instanceof Document) {
                        String found = findFieldValueOfType((Document) item, maxDepth - 1, clazz);
                        if (!found.isBlank()) {
                            return found;
                        }
                    } else {
                        break;
                    }
                }
            }
        }
        return "";
    }

    public static String findKeyMatchesPattern(Document document, int maxDepth, Pattern pattern) {
        if (pattern == null || maxDepth <= 0) {
            return "";
        }

        for (Map.Entry<String, Object> entry : document.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (pattern.matcher(key).find()) {
                return key;
            } else if (maxDepth > 1 && value instanceof Document) {
                key = findKeyMatchesPattern((Document) value, maxDepth - 1, pattern);
                if (!key.isBlank()) {
                    return key;
                }
            } else if (value instanceof List<?> valueList) {
                for (Object item : valueList) {
                    if (item instanceof Document) {
                        key = findKeyMatchesPattern((Document) item, maxDepth - 1, pattern);
                        if (!key.isBlank()) {
                            return key;
                        }
                    } else {
                        break;
                    }
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
            } else if (value instanceof List<?> valueList) {
                for (Object item : valueList) {
                    if (item instanceof Document) {
                        value = getValue((Document) item, maxDepth - 1, field);
                        if (value != null) {
                            return value;
                        }
                    } else {
                        break;
                    }
                }
            }
        }

        return null;
    }

    public static Object getValue(Document document, int maxDepth, Set<String> fieldList) {
        if (fieldList.isEmpty() || maxDepth <= 0) {
            return null;
        }

        for (String key : fieldList) {
            if (document.containsKey(key)) {
                return document.get(key);
            }
        }

        for (Object value : document.values()) {
            if (maxDepth > 1 && value instanceof Document) {
                value = getValue((Document) value, maxDepth - 1, fieldList);
                if (value != null) {
                    return value;
                }
            } else if (value instanceof List<?> valueList) {
                for (Object item : valueList) {
                    if (item instanceof Document) {
                        value = getValue((Document) item, maxDepth - 1, fieldList);
                        if (value != null) {
                            return value;
                        }
                    } else {
                        break;
                    }
                }
            }
        }

        return null;
    }
}
