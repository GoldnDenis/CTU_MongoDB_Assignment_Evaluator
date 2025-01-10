package cz.cvut.fel.mongodb_assignment_evaluator.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Stages {
    GROUP("$group"),
    LIMIT("$limit"),
    LOOKUP("$lookup"),
    MATCH("$match"),
    PROJECT("$project"),
    ADD_FIELDS("$addFields"),
    SKIP("$skip"),
    SORT("$sort");

    private final String value;
    private final int level = 1;
}
