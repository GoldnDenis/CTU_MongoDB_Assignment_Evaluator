package cz.cvut.fel.mongodb_assignment_evaluator.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public enum Stages {
    GROUP("$group"),
    LIMIT("$limit"),
    LOOKUP("$lookup"),
    MATCH("$match"),
    PROJECT_ADDFIELDS("$project", "$addFields"),
    SKIP("$skip"),
    SORT("$sort");

    Stages(String... stages) {
        this.stages = stages;
    }

    private final String[] stages;
}
