package cz.cvut.fel.task_evaluator.enums;

public enum QueryType {
    FIND,
    INSERT,
    UPDATE,
    AGGREGATE,
    CREATE_COLLECTION,
    REPLACE,
    UNKNOWN;

    public static QueryType fromString(String queryName) {
        if (queryName.contains("find")) {
            return FIND;
        } else if (queryName.contains("insert")) {
            return INSERT;
        } else if (queryName.contains("createCollection")) {
            return CREATE_COLLECTION;
        } else if (queryName.contains("update")) {
            return UPDATE;
        } else if (queryName.contains("aggregate")) {
            return AGGREGATE;
        } else if (queryName.contains("replace")) {
            return REPLACE;
        } else {
            return UNKNOWN;
        }
    }
}
