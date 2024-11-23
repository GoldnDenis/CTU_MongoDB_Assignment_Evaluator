package cz.cvut.fel.mongodb_assignment_evaluator.service.enums;

public enum QueryTypes {
    FIND,
    INSERT,
    UPDATE,
    AGGREGATE,
    CREATE_COLLECTION,
    REPLACE_ONE,
    FUNCTION,
    GENERAL,
    UNKNOWN;

    public static QueryTypes fromString(String queryName) {
        switch (queryName) {
            case "createCollection" -> {
                return QueryTypes.CREATE_COLLECTION;
            }
            case "insert", "insertOne", "insertMany" -> {
                return QueryTypes.INSERT;
            }
            case "updateOne", "updateMany" -> {
                return QueryTypes.UPDATE;
            }
            case "replaceOne" -> {
                return QueryTypes.REPLACE_ONE;
            }
            case "find" -> {
                return QueryTypes.FIND;
            }
            case "aggregate" -> {
                return QueryTypes.AGGREGATE;
            }
            default -> {
                return QueryTypes.UNKNOWN;
            }
        }
    }
}
