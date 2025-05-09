INSERT INTO CriterionGroup(name) VALUES
('GENERAL'),
('CREATE_COLLECTION'),
('INSERT'),
('UPDATE'),
('FIND'),
('AGGREGATE');

INSERT INTO Criterion(name, description, min_score, group_id) VALUES
('CREATE_ONE_COLLECTION', 'Explicitly create one or two collections', 1, 2),
('INSERT_TEN_DOCUMENTS', 'Insert 10 documents into each collection. These documents must be non-trivial', 10, 3),
('INSERT_DOCUMENT_EMBEDDED', 'Insert at least one document with an embedded object', 1, 3),
('INSERT_DOCUMENT_ARRAY', 'Insert at least one document with an array', 1, 3),
('INSERT_ONE_USED', 'Use insertOne operator', 1, 3),
('INSERT_MANY_USED', 'Use insertMany operator', 1, 3),
('REPLACE_VALUE', 'Replace the value of the document', 1, 4),
('UPDATE_ONE_DOCUMENT', 'Update an individual field of one document', 1, 4),
('UPDATE_MANY_FORMULA', 'Update an individual field in many documents by criterion; use a formula or increase/multiply the value', 1, 4),
('UPDATE_ADD_FIELD', 'Add a field in the document(s)', 1, 4),
('UPDATE_REMOVE_FIELD', 'Remove a field in the document(s)', 1, 4),
('UPDATE_NESTED_DOCUMENT', 'Modify a nested document', 1, 4),
('UPDATE_ARRAY', 'Modify an array', 1, 4),
('UPSERT_USED', 'Use the upsert mode at least once', 1, 4),
('FIND_FIVE', 'Express at least 5 find queries (with non-trivial selections, i.e. the first parameter cannot be empty)', 5, 5),
('FIND_LOGICAL_OPERATOR', 'Use at least one logical operator ($and, $or, $not)', 1, 5),
('FIND_COMPARISON', 'Use greater than or less than, etc.', 1, 5),
('FIND_ARRAY_OPERATOR', 'Use operation with array fields at least once', 1, 5),
('FIND_DATE_OPERATOR', 'Use operation with dates or parts of dates (for example, select all orders for a given date or all orders for a given year)', 1, 5),
('POSITIVE_PROJECTION', 'Use positive projection (at least once)', 1, 5),
('NEGATIVE_PROJECTION', 'Use negative projection (at least once)', 1, 5),
('SORT_MODIFIER', 'Use sort modifier at least once', 1, 5),
('AGGREGATE_FIVE', 'Express 5 aggregate operations. Use 2 created collections for entities of different types. If necessary, insert more documents into each one of them', 5, 6),
('STAGE_MATCH', 'Use at least once $match stage', 1, 6),
('STAGE_GROUP', 'Use at least once $group stage', 1, 6),
('STAGE_SORT', 'Use at least once $sort stage', 1, 6),
('STAGE_PROJECT_ADDFIELDS', 'Use at least once $project (or $addFields) stage', 1, 6),
('AGGREGATOR_SUM_AVG', 'Use at least once $sum (or $avg)', 1, 6),
('AGGREGATOR_COUNT', 'Use at least once $count', 1, 6),
('AGGREGATOR_MIN_MAX', 'Use at least once $min (or $max)', 1, 6),
('LOOKUP', 'Use lookup (if you have two collections)', 1, 6),
('COMMENT', 'Describe the real-world meaning of all your queries in comments', 1, 1);
