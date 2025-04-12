package cz.cvut.fel.mongodb_assignment_evaluator.domain.enums;

import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

@Getter
public enum Aggregations {
    COUNT(0, "$count"),
    MIN_MAX(0, "$min", "$max"),
    SUM_AVG(0, "$sum", "$avg"),
    GROUP(1, "$group"),
    LIMIT(1, "$limit"),
    LOOKUP(1, "$lookup"),
    MATCH(1, "$match"),
    PROJECT_ADDFIELDS(1, "$project", "$addFields"),
    SKIP(1, "$skip"),
    SORT(1, "$sort");

    Aggregations(int level, String... aggregations) {
        this.level = level;
        this.aggregations = new HashSet<>(Set.of(aggregations));
    }

    private final int level;
    private final Set<String> aggregations;
}
