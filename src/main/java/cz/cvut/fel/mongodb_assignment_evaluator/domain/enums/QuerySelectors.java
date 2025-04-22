package cz.cvut.fel.mongodb_assignment_evaluator.domain.enums;

import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

@Getter
public enum QuerySelectors {
    LOGICAL("$and", "$or", "$not", "$nor"),
    ARRAY("$all", "$elemMatch", "$in", "$nin", "$size"),
    COMPARISON("$eq", "$ne", "$gt", "$gte", "$lt", "$lte");

    QuerySelectors(String... operators) {
        this.operators = new HashSet<>(Set.of(operators));
    }

    private final Set<String> operators;
}
