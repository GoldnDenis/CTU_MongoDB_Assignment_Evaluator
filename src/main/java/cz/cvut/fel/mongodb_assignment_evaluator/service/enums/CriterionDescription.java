package cz.cvut.fel.mongodb_assignment_evaluator.service.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CriterionDescription {
    COMMENT("Describe the real-world meaning of all your queries in comments", 1),

    CREATE_ONE_COLLECTION("Explicitly create one or two collections", 1),

    INSERT_TEN_DOCUMENTS("Insert 10 documents into each collection", 10),
    INSERT_DOCUMENT_NON_TRIVIAL("These documents must be non-trivial", 1),
    INSERT_DOCUMENT_EMBEDDED_OBJECTS("These documents must be with embedded objects", 1),
    INSERT_DOCUMENT_ARRAYS("These documents must be with arrays", 1),
    INSERT_DOCUMENTS_INTERLINK("Interlink the documents using references", 1),
    INSERT_ONE_USED("Use insertOne", 1),
    INSERT_MANY_USED("Use insertMany", 1),

    REPLACE_VALUE("Replace the value of the document", 1),
    UPDATE_ONE_DOCUMENT("Update an individual field of one document", 1),
    UPDATE_INCREASE_MULTIPLY("Update an individual field in documents by criterion; use a formula or increase/multiply the value", 1),
    UPDATE_ADD_FIELD("Add a field in the document(s)", 1),
    UPDATE_REMOVE_FIELD("Remove a field in the document(s)", 1),
    UPDATE_NESTED_DOCUMENT("Modify a nested document", 1),
    UPDATE_ARRAY_REPLACE("Replace a single array element", 1),
    UPDATE_ARRAY_ADD("Add a single array element", 1),
    UPDATE_ARRAY_REMOVE("Delete a single array element", 1),
    UPDATE_UPSERT_USED("Use the upsert mode at least once", 1),

    FIND_FIVE_QUERIES("Express at least 5 find queries (with non-trivial selections, i.e. the first parameter cannot be empty)", 5),
    FIND_LOGICAL_OPERATOR("Use at least one logical operator ($and, $or, $not)", 1),
    FIND_ELEM_MATCH_USED("Use $elemMatch operator on array fields at least once", 1),
    FIND_POSITIVE_PROJECTION_USED("Use positive projection (at least once)", 1),
    FIND_NEGATIVE_PROJECTION_USED("Use negative projection (at least once)", 1),
    FIND_SORT_MODIFIER_USED("Use sort modifier", 1),
    FIND_VALUE_USED("The search criteria should contain matching the value of one or more fields (at least once)", 1),
    FIND_CONDITION_USED("The search criteria should contain condition on the value of one or more fields, e.g. containing greater than or less than, etc. (at least once)", 1),
    FIND_DATE_COMPARISON_USED("The search criteria should contain comparing dates or parts of dates, e.g. all orders for a given date or all orders for a given year (at least once)", 1),
    FIND_ARRAY_USED("The search criteria should contain matching the whole array (at least once)", 1),
    FIND_ARRAY_VALUE_USED("The search criteria should contain searching for a value in the array (at least once)", 1),
    FIND_LOOKUP_USED("Use lookup (if you have two collections)", 1),

    AGGREGATE_FIVE("Express 5 aggregate operations. Use 2 created collections for entities of different types. If necessary, insert more documents into each one of them", 5),
    AGGREGATE_MATCH_STAGE("Use at least once $match stage", 1),
    AGGREGATE_GROUP_STAGE("Use at least once $group stage", 1),
    AGGREGATE_SORT_STAGE("Use at least once $sort stage", 1),
    AGGREGATE_PROJECT_ADD_FIELDS_STAGE("Use at least once $project (or $addFields) stage", 1),
    AGGREGATE_SKIP_STAGE("Use at least once $skip stage", 1),
    AGGREGATE_LIMIT_STAGE("Use at least once $limit stage", 1),
    AGGREGATE_SUM_AVG_AGGREGATOR("Use at least once $sum (or $avg) aggregator", 1),
    AGGREGATE_COUNT_AGGREGATOR("Use at least once $count aggregator", 1),
    AGGREGATE_MIN_MAX_AGGREGATOR("Use at least once $min (or $max) aggregator", 1),
    AGGREGATE_FIRST_LAST_AGGREGATOR("Use at least once $first (or $last)) aggregator", 1);
    
    private final String description;
    private final int requiredCount;
}
