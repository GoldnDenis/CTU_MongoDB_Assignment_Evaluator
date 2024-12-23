package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.bson;

import org.bson.BsonDocument;
import org.bson.BsonValue;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

public class BsonChecker {
    public static Set<String> findAllFieldsOfType(BsonDocument document, Class<?> clazz) {
        Set<String> fields = new HashSet<>();
        for (Map.Entry<String, BsonValue> entry : document.entrySet()) {
            String key = entry.getKey();
            BsonValue value = entry.getValue();
            if (value == null) {
                continue;
            }
            if (!key.startsWith("$") &&
                    clazz.isInstance(value)) {
                fields.add(key);
            } else if (value.isDocument()) {
                fields.addAll(findAllFieldsOfType(value.asDocument(), clazz));
            } else if (value.isArray()) {
                for (BsonValue item : value.asArray()) {
                    if (!key.startsWith("$") &&
                            clazz.isInstance(item)) {
                        fields.add(key);
                        break;
                    } else if (item.isDocument()) {
                        fields.addAll(findAllFieldsOfType(item.asDocument(), clazz));
                    } else {
                        break;
                    }
                }
            }
        }
        return fields;
    }

    public static String findFieldValueOfType(BsonDocument document, int maxDepth, Class<?> clazz) {
        if (maxDepth <= 0) {
            return "";
        }

        for (Map.Entry<String, BsonValue> entry : document.entrySet()) {
            String key = entry.getKey();
            BsonValue value = entry.getValue();
            if (value == null) {
                continue;
            }
            if (!key.startsWith("$") &&
                    clazz.isInstance(value)) {
                return key;
            } else if (maxDepth > 1 && value.isDocument()) {
                String found = findFieldValueOfType(value.asDocument(), maxDepth - 1, clazz);
                if (!found.isBlank()) {
                    return found;
                }
            } else if (value.isArray()) {
                for (BsonValue item : value.asArray()) {
                    if (clazz.isInstance(item)) {
                        return key;
                    } else if (item.isDocument()) {
                        String found = findFieldValueOfType(item.asDocument(), maxDepth - 1, clazz);
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

    public static String findKeyMatchesPattern(BsonDocument document, int maxDepth, Pattern pattern) {
        if (pattern == null || maxDepth <= 0) {
            return "";
        }

        for (Map.Entry<String, BsonValue> entry : document.entrySet()) {
            String key = entry.getKey();
            BsonValue value = entry.getValue();
            if (value == null) {
                continue;
            }
            if (pattern.matcher(key).find()) {
                return key;
            } else if (maxDepth > 1 && value.isDocument()) {
                key = findKeyMatchesPattern(value.asDocument(), maxDepth - 1, pattern);
                if (!key.isBlank()) {
                    return key;
                }
            } else if (value.isArray()) {
                for (BsonValue item : value.asArray()) {
                    if (item.isDocument()) {
                        key = findKeyMatchesPattern(item.asDocument(), maxDepth - 1, pattern);
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

    public static BsonValue getValue(BsonDocument document, int maxDepth, String field) {
        if (field.isBlank() || maxDepth <= 0) {
            return null;
        }

        if (document.containsKey(field)) {
            return document.get(field);
        }

        for (BsonValue value : document.values()) {
            if (maxDepth > 1 && value.isDocument()) {
                value = getValue(value.asDocument(), maxDepth - 1, field);
                if (value != null) {
                    return value;
                }
            } else if (value.isArray()) {
                for (BsonValue item : value.asArray()) {
                    if (item.isDocument()) {
                        value = getValue(item.asDocument(), maxDepth - 1, field);
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

    public static BsonValue getValue(BsonDocument document, int maxDepth, Set<String> fieldList) {
        if (fieldList.isEmpty() || maxDepth <= 0) {
            return null;
        }

        for (String key : fieldList) {
            if (document.containsKey(key)) {
                return document.get(key);
            }
        }

        for (BsonValue value : document.values()) {
            if (maxDepth > 1 && value.isDocument()) {
                value = getValue(value.asDocument(), maxDepth - 1, fieldList);
                if (value != null) {
                    return value;
                }
            } else if (value.isArray()) {
                for (BsonValue item : value.asArray()) {
                    if (item.isDocument()) {
                        value = getValue(item.asDocument(), maxDepth - 1, fieldList);
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
