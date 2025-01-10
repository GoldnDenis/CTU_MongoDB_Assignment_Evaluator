package cz.cvut.fel.mongodb_assignment_evaluator.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum QueryTypes {
    FIND(true),
    INSERT(true),
    UPDATE(true),
    AGGREGATE(true),
    CREATE_COLLECTION(false),
    REPLACE_ONE(true),
    GENERAL(false),
    UNKNOWN(false);

    private final boolean inCollectionScope;

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
            case "find", "findOne" -> {
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
