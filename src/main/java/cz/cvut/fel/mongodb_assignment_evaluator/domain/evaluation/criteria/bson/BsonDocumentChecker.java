package cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.criteria.bson;

import org.bson.BsonArray;
import org.bson.BsonDocument;
import org.bson.BsonType;
import org.bson.BsonValue;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BsonDocumentChecker {
    public static Optional<String> findKeyMatchesPattern(BsonDocument document, Pattern pattern) {
        return findKeyMatchesPattern(document, pattern, getDepth(document));
    }

    public static Optional<String> findKeyMatchesPattern(BsonDocument document, Pattern pattern, int depth) {
        if (depth <= 0 || document == null) {
            return Optional.empty();
        }
        for (Map.Entry<String, BsonValue> entry : document.entrySet()) {
            String key = entry.getKey();
            BsonValue value = entry.getValue();
            Matcher matcher = pattern.matcher(key);
            if (matcher.find()) {
                return Optional.of(matcher.group());
            }
            if (value.isDocument()) {
                Optional<String> found = findKeyMatchesPattern(value.asDocument(), pattern, depth - 1);
                if (found.isPresent()) {
                    return found;
                }
            } else if (value.isArray()) {
                for (BsonValue arrayValue : value.asArray()) {
                    if (arrayValue.isDocument()) {
                        Optional<String> found = findKeyMatchesPattern(arrayValue.asDocument(), pattern, depth - 1);
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

    public static Optional<String> findValueMatchesPattern(BsonDocument document, Pattern pattern) {
        return findValueMatchesPattern(document, pattern, getDepth(document));
    }

    public static Optional<String> findValueMatchesPattern(BsonDocument document, Pattern pattern, int depth) {
        if (depth <= 0 || document == null) {
            return Optional.empty();
        }
        for (BsonValue value : document.values()) {
            if (value.isString()) {
                String valueString = value.asString().getValue();
                Matcher matcher = pattern.matcher(valueString);
                if (matcher.find()) {
                    return Optional.of(matcher.group());
                }
            } else if (value.isDocument()) {
                Optional<String> found = findValueMatchesPattern(value.asDocument(), pattern, depth - 1);
                if (found.isPresent()) {
                    return found;
                }
            } else if (value.isArray()) {
                for (BsonValue arrayValue : value.asArray()) {
                    if (arrayValue.isString()) {
                        String valueString = arrayValue.asString().toString();
                        Matcher matcher = pattern.matcher(valueString);
                        if (matcher.find()) {
                            return Optional.of(matcher.group());
                        }
                    } else if (arrayValue.isDocument()) {
                        Optional<String> found = findValueMatchesPattern(arrayValue.asDocument(), pattern, depth - 1);
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

    public static boolean containsValue(BsonDocument document, BsonValue value) {
        return containsValue(document, value, getDepth(document));
    }

    public static boolean containsValue(BsonDocument document, BsonValue value, int depth) {
        if (depth <= 0 || document == null) {
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
                if (containsValue(curValue.asDocument(), value, depth - 1)) {
                    return true;
                }
            } else if (curValue.isArray()) {
                BsonArray array = value.asArray();
                if (array.contains(value)) {
                    return true;
                }
                for (BsonValue arrayValue : array) {
                    if (!arrayValue.isDocument()) {
                        break;
                    }
                    if (containsValue(arrayValue.asDocument(), value, depth - 1)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static Optional<BsonValue> getRecursive(BsonDocument document, String key) {
        return getRecursive(document, key, getDepth(document));
    }

    public static Optional<BsonValue> getRecursive(BsonDocument document, String key, int depth) {
        if (depth <= 0 || document == null) {
            return Optional.empty();
        }
        if (document.containsKey(key)) {
            return Optional.of(document.get(key));
        }
        for (BsonValue value : document.values()) {
            if (value.isDocument()) {
                Optional<BsonValue> foundValue = getRecursive(value.asDocument(), key, depth - 1);
                if (foundValue.isPresent()) {
                    return foundValue;
                }
            } else if (value.isArray()) {
                for (BsonValue arrayValue : value.asArray()) {
                    if (!arrayValue.isDocument()) {
                        break;
                    }
                    Optional<BsonValue> foundValue = getRecursive(arrayValue.asDocument(), key, depth - 1);
                    if (foundValue.isPresent()) {
                        return foundValue;
                    }
                }
            }
        }
        return Optional.empty();
    }

    public static List<BsonValue> getAll(BsonDocument document, Set<String> keySet) {
        return getAll(document, keySet, getDepth(document));
    }

    public static List<BsonValue> getAll(BsonDocument document, Set<String> keySet, int depth) {
        List<BsonValue> foundValues = new ArrayList<>();
        if (depth <= 0 || document == null) {
            return foundValues;
        }
        for (Map.Entry<String, BsonValue> entry : document.entrySet()) {
            String key = entry.getKey();
            BsonValue value = entry.getValue();
            if (keySet.contains(key)) {
                foundValues.add(value);
            }
            if (value.isDocument()) {
                foundValues.addAll(getAll(value.asDocument(), keySet, depth - 1));
            } else if (value.isArray()) {
                for (BsonValue arrayValue : value.asArray()) {
                    if (!arrayValue.isDocument()) {
                        break;
                    }
                    foundValues.addAll(getAll(arrayValue.asDocument(), keySet, depth - 1));
                }
            }
        }
        return foundValues;
    }

    public static boolean containsValueOfType(BsonDocument document, BsonType type) {
        return containsValueOfType(document, type, getDepth(document));
    }

    public static boolean containsValueOfType(BsonDocument document, BsonType type, int depth) {
        if (depth <= 0 || document == null) {
            return false;
        }
        for (BsonValue value : document.values()) {
            if (value.getBsonType().equals(type)) {
                return true;
            }
            if (value.isDocument()) {
                if (containsValueOfType(value.asDocument(), type, depth - 1)) {
                    return true;
                }
            } else if (value.isArray()) {
                for (BsonValue arrayValue : value.asArray()) {
                    if (arrayValue.getBsonType().equals(type)) {
                        return true;
                    }
                    if (!arrayValue.isDocument()) {
                        break;
                    }
                    if (containsValueOfType(arrayValue.asDocument(), type, depth - 1)) {
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
                for (BsonValue arrayValue : value.asArray()) {
                    if (!arrayValue.isDocument()) {
                        break;
                    }
                    maxDepth = Math.max(maxDepth, getDepth(arrayValue.asDocument()));
                }
            }
        }
        return maxDepth + 1;
    }
}
