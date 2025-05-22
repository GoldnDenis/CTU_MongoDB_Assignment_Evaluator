package cz.cvut.fel.mongodb_assignment_evaluator.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MongoCommands {
    CREATE_COLLECTION("createCollection"),
    INSERT("insert"),
    INSERT_ONE("insertOne"),
    INSERT_MANY("insertMany"),
    UPDATE_ONE("updateOne"),
    UPDATE_MANY("updateMany"),
    REPLACE_ONE("replaceOne"),
    FIND("find"),
    FIND_ONE("findOne"),
    AGGREGATE("aggregate"),
    SORT("sort"),
    UNRECOGNIZED("unrecognized");

    public static MongoCommands fromString(String operator) {
        switch (operator.toUpperCase()) {
            case "CREATECOLLECTION" -> {
                return MongoCommands.CREATE_COLLECTION;
            }
            case "INSERT" -> {
                return MongoCommands.INSERT;
            }
            case "INSERTONE" -> {
                return MongoCommands.INSERT_ONE;
            }
            case "INSERTMANY" -> {
                return MongoCommands.INSERT_MANY;
            }
            case "UPDATEONE" -> {
                return MongoCommands.UPDATE_ONE;
            }
            case "UPDATEMANY" -> {
                return MongoCommands.UPDATE_MANY;
            }
            case "REPLACEONE" -> {
                return MongoCommands.REPLACE_ONE;
            }
            case "FIND" -> {
                return MongoCommands.FIND;
            }
            case "FINDONE" -> {
                return MongoCommands.FIND_ONE;
            }
            case "AGGREGATE" -> {
                return MongoCommands.AGGREGATE;
            }
            case "SORT" -> {
                return MongoCommands.SORT;
            }
            default -> {
                return MongoCommands.UNRECOGNIZED;
            }
        }
    }

    private final String command;
}
