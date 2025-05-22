package cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.grader.bson;

import org.bson.BsonArray;
import org.bson.BsonDocument;
import org.bson.BsonType;
import org.bson.BsonValue;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utility class for recursively validating BsonDocuments
 */
public class BsonDocumentChecker {
    /**
     * Looks through the entire document recursively to find a key that matches the pattern
     * @param document
     * @param pattern
     * @return first key inside a document that matched the pattern
     */
    public static Optional<String> findKeyMatchesPattern(BsonDocument document, Pattern pattern) {
        return findKeyMatchesPattern(document, pattern, getDepth(document));
    }

    /**
     * Looks through the document, but not deeper than specified, recursively to find a key that matches the pattern
     * @param document
     * @param pattern
     * @param depth level at which it stops going deeper
     * @return first key inside a document that matched the pattern
     */
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

    /**
     * Looks through the entire document recursively to find a string value that matches the pattern
     * @param document
     * @param pattern
     * @return first string value inside a document that matched the pattern
     */
    public static Optional<String> findValueMatchesPattern(BsonDocument document, Pattern pattern) {
        return findValueMatchesPattern(document, pattern, getDepth(document));
    }

    /**
     * Looks through the document, but not deeper than specified, recursively to find a string value that matches the pattern
     * @param document
     * @param pattern
     * @param depth level at which it stops going deeper
     * @return first string value inside a document that matched the pattern
     */
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

    /**
     * Looks through the entire document recursively to find a value
     * @param document
     * @param value
     * @return if value was found
     */
    public static boolean containsValue(BsonDocument document, BsonValue value) {
        return containsValue(document, value, getDepth(document));
    }

    /**
     * Looks through the document, but not deeper than specified, recursively to find a value
     * @param document
     * @param value
     * @param depth level at which it stops going deeper
     * @return if value was found
     */
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

    /**
     * Looks through the entire document recursively to find a key and retrieve its value
     * @param document
     * @param key
     * @return key's value
     */
    public static Optional<BsonValue> getRecursive(BsonDocument document, String key) {
        return getRecursive(document, key, getDepth(document));
    }

    /**
     * Looks through the document, but not deeper than specified, recursively to find a key and retrieve its value
     * @param document
     * @param key
     * @param depth level at which it stops going deeper
     * @return key's value
     */
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

    /**
     * Looks through the document, but not deeper than specified, recursively to find a key and retrieve its value.
     * It does not have to find all of them, it just looks to find at least something. Otherwise, the list is empty.
     * @param document
     * @param keySet
     * @return A list of all values of every found key
     */
    public static List<BsonValue> getAll(BsonDocument document, Set<String> keySet) {
        return getAll(document, keySet, getDepth(document));
    }

    /**
     * Looks through the document, but not deeper than specified, recursively to find all keys and retrieve their value.
     * It does not have to find all of them, it just looks to find at least something. Otherwise, the list is empty.
     * @param document
     * @param keySet
     * @param depth level at which it stops going deeper
     * @return A list of all values of every found key
     */
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

    /**
     * Looks through the document, but not deeper than specified, recursively to find any value that matches the type
     * @param document
     * @param type
     * @return if any of type was found
     */
    public static boolean containsValueOfType(BsonDocument document, BsonType type) {
        return containsValueOfType(document, type, getDepth(document));
    }

    /**
     * Looks through the document, but not deeper than specified, recursively to find any value that matches the type
     * @param document
     * @param type
     * @param depth level at which it stops going deeper
     * @return if any of type was found
     */
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

    /**
     * Employs the DFS algorithm to compute the depth of a document
     * @param document
     * @return maximum depth
     */
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
