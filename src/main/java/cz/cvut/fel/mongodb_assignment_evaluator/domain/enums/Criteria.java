package cz.cvut.fel.mongodb_assignment_evaluator.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Criteria {
    CREATE_ONE_COLLECTION("Explicitly create one or two collections", 1),

    INSERT_TEN_DOCUMENTS("Insert 10 documents into each collection. These documents must be non-trivial", 10),
    INSERT_DOCUMENT_EMBEDDED("Insert at least one document with an embedded object", 1),
    INSERT_DOCUMENT_ARRAY("Insert at least one document with an array", 1),
    INSERT_ONE_USED("Use insertOne operator", 1),
    INSERT_MANY_USED("Use insertMany operator", 1),

    REPLACE_VALUE("Replace the value of the document", 1),

    UPDATE_ONE_DOCUMENT("Update an individual field of one document", 1),
    UPDATE_MANY_FORMULA("Update an individual field in many documents by criterion; use a formula or increase/multiply the value", 1),
    UPDATE_ADD_FIELD("Add a field in the document(s)", 1),
    UPDATE_REMOVE_FIELD("Remove a field in the document(s)", 1),
    UPDATE_NESTED_DOCUMENT("Modify a nested document", 1),
    UPDATE_ARRAY("Modify an array", 1),
//    UPDATE_ARRAY_REPLACE("Replace a single array element", 1),
//    UPDATE_ARRAY_ADD("Add a single array element", 1),
//    UPDATE_ARRAY_REMOVE("Delete a single array element", 1),
    UPSERT_USED("Use the upsert mode at least once", 1),

    FIND_FIVE("Express at least 5 find queries (with non-trivial selections, i.e. the first parameter cannot be empty)", 5),
    FIND_LOGICAL_OPERATOR("Use at least one logical operator ($and, $or, $not)", 1),
    FIND_COMPARISON("Use greater than or less than, etc.", 1),
    FIND_ARRAY_OPERATOR("Use operation with array fields at least once", 1),
    FIND_DATE_OPERATOR("Use operation with dates or parts of dates (for example, select all orders for a given date or all orders for a given year)", 1),
    POSITIVE_PROJECTION("Use positive projection (at least once)", 1),
    NEGATIVE_PROJECTION("Use negative projection (at least once)", 1),
    SORT_MODIFIER("Use sort modifier at least once", 1),

    AGGREGATE_FIVE("Express 5 aggregate operations. Use 2 created collections for entities of different types. If necessary, insert more documents into each one of them", 5),
    STAGE_MATCH("Use at least once $match stage", 1),
    STAGE_GROUP("Use at least once $group stage", 1),
    STAGE_SORT("Use at least once $sort stage", 1),
    STAGE_PROJECT_ADDFIELDS("Use at least once $project (or $addFields) stage", 1),
    AGGREGATOR_SUM_AVG("Use at least once $sum (or $avg)", 1),
    AGGREGATOR_COUNT("Use at least once $count", 1),
    AGGREGATOR_MIN_MAX("Use at least once $min (or $max)", 1),
    LOOKUP("Use lookup (if you have two collections)", 1), //worth mentioning that there could be only one collection

    UNRECOGNIZED_QUERY("Queries were not recognized", 0),

    COMMENT("Describe the real-world meaning of all your queries in comments", 1);

    public static int getPriority(String criterionName) {
        try {
            return Criteria.valueOf(criterionName).ordinal();
        } catch (IllegalArgumentException e) {
            return -1;
        }
    }

//    ELEMMATCH_USED("Use $elemMatch operator on array fields at least once", 1),
//    FIND_VALUE("The search criteria should contain matching the value of one or more fields (at least once)", 1),
//    FIND_CONDITION("The search criteria should contain condition on the value of one or more fields, e.g. containing greater than or less than, etc. (at least once)", 1),
//    FIND_DATE_COMPARISON("The search criteria should contain comparing dates or parts of dates, e.g. all orders for a given date or all orders for a given year (at least once)", 1),
//    FIND_ARRAY("The search criteria should contain matching the whole array (at least once)", 1),
//    FIND_ARRAY_VALUE("The search criteria should contain searching for a value in the array (at least once)", 1),
//    INTERLINK("Interlink the documents using references", 1),
//    STAGE_SKIP("Use at least once $skip stage", 1),
//    STAGE_LIMIT("Use at least once $limit stage", 1),
//    AGGREGATOR_FIRST_LAST("Use at least once $first (or $last)) aggregator", 1),

//    public static Criteria fromString(String name) {
//        name = name.toUpperCase();
//        switch (name) {
//            case "CREATE_ONE_COLLECTION" -> {
//                return Criteria.CREATE_ONE_COLLECTION;
//            }
//            case "INSERT_TEN_DOCUMENTS" -> {
//                return Criteria.INSERT_TEN_DOCUMENTS;
//            }
//            case "INSERT_DOCUMENT_EMBEDDED" -> {
//                return Criteria.INSERT_DOCUMENT_EMBEDDED;
//            }
//            case "INSERT_DOCUMENT_ARRAY" -> {
//                return Criteria.INSERT_DOCUMENT_ARRAY;
//            }
//            case "INSERT_ONE_USED" -> {
//                return Criteria.INSERT_ONE_USED;
//            }
//            case "INSERT_MANY_USED" -> {
//                return Criteria.INSERT_MANY_USED;
//            }
//            case "REPLACE_VALUE" -> {
//                return Criteria.REPLACE_VALUE;
//            }
//            case "UPDATE_ONE_DOCUMENT" -> {
//                return Criteria.UPDATE_ONE_DOCUMENT;
//            }
//            case "UPDATE_MANY_FORMULA" -> {
//                return Criteria.UPDATE_MANY_FORMULA;
//            }
//            case "UPDATE_ADD_FIELD" -> {
//                return Criteria.UPDATE_ADD_FIELD;
//            }
//            case "UPDATE_REMOVE_FIELD" -> {
//                return Criteria.UPDATE_REMOVE_FIELD;
//            }
//            case "UPDATE_NESTED_DOCUMENT" -> {
//                return Criteria.UPDATE_NESTED_DOCUMENT;
//            }
//            case "UPDATE_ARRAY" -> {
//                return Criteria.UPDATE_ARRAY;
//            }
//            case "UPSERT_USED" -> {
//                return Criteria.UPSERT_USED;
//            }
//            case "FIND_FIVE" -> {
//                return Criteria.FIND_FIVE;
//            }
//            case "FIND_LOGICAL_OPERATOR" -> {
//                return Criteria.FIND_LOGICAL_OPERATOR;
//            }
//            case "FIND_COMPARISON" -> {
//                return Criteria.FIND_COMPARISON;
//            }
//            case "FIND_ARRAY_OPERATOR" -> {
//                return Criteria.FIND_ARRAY_OPERATOR;
//            }
//            case "FIND_DATE_OPERATOR" -> {
//                return Criteria.FIND_DATE_OPERATOR;
//            }
//            case "POSITIVE_PROJECTION" -> {
//                return Criteria.POSITIVE_PROJECTION;
//            }
//            case "NEGATIVE_PROJECTION" -> {
//                return Criteria.NEGATIVE_PROJECTION;
//            }
//            case "SORT_MODIFIER" -> {
//                return Criteria.SORT_MODIFIER;
//            }
//            case "AGGREGATE_FIVE" -> {
//                return Criteria.AGGREGATE_FIVE;
//            }
//            case "STAGE_MATCH" -> {
//                return Criteria.STAGE_MATCH;
//            }
//            case "STAGE_GROUP" -> {
//                return Criteria.STAGE_GROUP;
//            }
//            case "STAGE_SORT" -> {
//                return Criteria.STAGE_SORT;
//            }
//            case "STAGE_PROJECT_ADDFIELDS" -> {
//                return Criteria.STAGE_PROJECT_ADDFIELDS;
//            }
//            case "AGGREGATOR_SUM_AVG" -> {
//                return Criteria.AGGREGATOR_SUM_AVG;
//            }
//            case "AGGREGATOR_COUNT" -> {
//                return Criteria.AGGREGATOR_COUNT;
//            }
//            case "AGGREGATOR_MIN_MAX" -> {
//                return Criteria.AGGREGATOR_MIN_MAX;
//            }
//            case "LOOKUP" -> {
//                return Criteria.LOOKUP;
//            }
//            case "UNRECOGNISED_QUERY" -> {
//                return Criteria.UNRECOGNISED_QUERY;
//            }
//            case "COMMENT" -> {
//                return Criteria.COMMENT;
//            }
//            default -> {
//                return Criteria.UNRECOGNISED_CRITERION;
//            }
//        }
//    }

    private final String description;
    private final int requiredCount;
}
