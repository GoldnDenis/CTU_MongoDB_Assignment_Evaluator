package cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.checker.bson;

import org.bson.BsonDocument;
import org.bson.BsonType;
import org.bson.BsonValue;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BsonDocumentChecker {
    public static boolean containsImplicitEquality(BsonDocument document) {
        for (Map.Entry<String, BsonValue> entry : document.entrySet()) {
            String key = entry.getKey();
            BsonValue value = entry.getValue();
            if (key.startsWith("$")) {
                continue;
            }
            if (value.isDocument() || value.isArray()) {
                continue;
            }
            return true;
        }
        return false;
    }

    public static Optional<String> findKeyMatchesPattern(BsonDocument document, Pattern pattern) {
        return findKeyMatchesPattern(document, pattern, getDepth(document));
    }

    public static Optional<String> findKeyMatchesPattern(BsonDocument document, Pattern pattern, int depth) {
        if (depth < 0 || document == null) {
            return Optional.empty();
        }
        for (Map.Entry<String, BsonValue> entry : document.entrySet()) {
            String key = entry.getKey();
            BsonValue value = entry.getValue();
            Matcher matcher = pattern.matcher(key);
            if (matcher.find()) {
                return Optional.of(matcher.group().split("\\.")[0]);
            }
            if (value.isDocument()) {
                Optional<String> found = findKeyMatchesPattern(value.asDocument(), pattern, depth - 1);
                if (found.isPresent()) {
                    return found;
                }
            } else if (value.isArray()) {
                for (BsonValue nestedValue: value.asArray()) {
                    if (nestedValue.isString()) {
                        matcher = pattern.matcher(nestedValue.asString().getValue());
                        if (matcher.find()) {
                            return Optional.of(matcher.group().split("\\.")[0]);
                        }
                    } else if (nestedValue.isDocument()) {
                        Optional<String> found = findKeyMatchesPattern(nestedValue.asDocument(), pattern, depth - 1);
                        if (found.isPresent()) {
                            return found;
                        }
                    } else {
                        break;
                    }
                }
            }
        }
        return Optional.empty();
    }

    public static boolean containsRecursive(BsonDocument document, BsonValue value) {
        return containsRecursive(document, value, getDepth(document));
    }

    public static boolean containsRecursive(BsonDocument document, BsonValue value, int depth) {
        if (depth < 0 || document == null) {
            return false;
        }
        if (value.isString() &&
            document.containsKey(value.asString().getValue())) {
            return true;
        }
        if (document.containsValue(value)) {
            return true;
        }
        for (BsonValue curValue : document.values()) {
            if (curValue.isDocument()) {
                if (containsRecursive(curValue.asDocument(), value, depth - 1)) {
                    return true;
                }
            } else if (curValue.isArray()) {
                if (curValue.asArray().contains(value)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static Optional<BsonValue> getRecursive(BsonDocument document, String key) {
        return getRecursive(document, key, getDepth(document));
    }

    public static Optional<BsonValue> getRecursive(BsonDocument document, String key, int depth) {
        if (depth < 0 || document == null) {
            return Optional.empty();
        }
        if (document.containsKey(key)) {
            return Optional.of(document.get(key));
        }
        for (BsonValue value : document.values()) {
            if (value.isDocument()) {
                Optional<BsonValue> result = getRecursive(value.asDocument(), key, depth - 1);
                if (result.isPresent()) {
                    return result;
                }
            } else if (value.isArray()) {
                for (BsonValue nestedValue : value.asArray()) {
                    if (!nestedValue.isDocument()) {
                        break;
                    }
                    Optional<BsonValue> result = getRecursive(nestedValue.asDocument(), key, depth - 1);
                    if (result.isPresent()) {
                        return result;
                    }
                }
            }
        }
        return Optional.empty();
    }

    public static List<BsonValue> getAllRecursive(BsonDocument document, Set<String> keySet) {
        return getAllRecursive(document, keySet, getDepth(document));
    }

    public static List<BsonValue> getAllRecursive(BsonDocument document, Set<String> keySet, int depth) {
        List<BsonValue> result = new ArrayList<>();
        if (depth < 0 || document == null) {
            return Collections.emptyList();
        }
        for (Map.Entry<String, BsonValue> entry : document.entrySet()) {
            String key = entry.getKey();
            BsonValue value = entry.getValue();
            if (keySet.contains(key)) {
                result.add(value);
            }
            if (value.isDocument()) {
                result.addAll(getAllRecursive(value.asDocument(), keySet, depth - 1));
            } else if (value.isArray()) {
                for (BsonValue nestedValue : value.asArray()) {
                    if (!nestedValue.isDocument()) {
                        break;
                    }
                    result.addAll(getAllRecursive(nestedValue.asDocument(), keySet, depth - 1));
                }
            }
        }
        return result;
    }

    public static boolean containsValueOfType(BsonDocument document, BsonType type) {
        return containsValueOfType(document, type, getDepth(document));
    }

    public static boolean containsValueOfType(BsonDocument document, BsonType type, int depth) {
        if (depth < 0 || document == null) {
            return false;
        }
        for (BsonValue value : document.values()) {
            if (value.getBsonType() == type) {
                return true;
            }
            if (value.isDocument()) {
                if (containsValueOfType(value.asDocument(), type, depth - 1)) {
                    return true;
                }
            } else if (value.isArray()) {
                for (BsonValue nestedValue: value.asArray()) {
                    if (value.getBsonType() == type) {
                        return true;
                    }
                    if (!nestedValue.isDocument()) {
                        break;
                    }
                    if (containsValueOfType(nestedValue.asDocument(), type, depth - 1)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static int getDepth(BsonDocument document) {
        if (document == null || document.isEmpty()) {
            return 0;
        }
        int maxDepth = 0;
        for (BsonValue value : document.values()) {
            if (value.isDocument()) {
                maxDepth = Math.max(maxDepth, getDepth(value.asDocument()));
            } else if (value.isArray()) {
                for (BsonValue nestedValue : value.asArray()) {
                    if (!nestedValue.isDocument()) {
                        break;
                    }
                    maxDepth = Math.max(maxDepth, getDepth(nestedValue.asDocument()));
                }
            }
        }
        return maxDepth + 1;
    }
}
