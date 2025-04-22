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

    private final String description;
    private final int requiredCount;
}
