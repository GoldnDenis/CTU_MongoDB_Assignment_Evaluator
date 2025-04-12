package cz.cvut.fel.mongodb_assignment_evaluator.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Operators {
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

    public static Operators fromString(String operator) {
      switch (operator.toUpperCase()) {
          case "CREATECOLLECTION" -> {
              return Operators.CREATE_COLLECTION;
          }
          case "INSERT" -> {
              return Operators.INSERT;
          }
          case "INSERTONE" -> {
              return Operators.INSERT_ONE;
          }
          case "INSERTMANY" -> {
              return Operators.INSERT_MANY;
          }
          case "UPDATEONE" -> {
              return Operators.UPDATE_ONE;
          }
          case "UPDATEMANY" -> {
              return Operators.UPDATE_MANY;
          }
          case "REPLACEONE" -> {
              return Operators.REPLACE_ONE;
          }
          case "FIND" -> {
              return Operators.FIND;
          }
          case "FINDONE" -> {
              return Operators.FIND_ONE;
          }
          case "AGGREGATE" -> {
              return Operators.AGGREGATE;
          }
          case "SORT" -> {
              return Operators.SORT;
          }
          default -> {
              return Operators.UNRECOGNIZED;
          }
      }
    }

    private final String operator;
}
