package cz.cvut.fel.mongodb_assignment_evaluator.service.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Aggregators {
    COUNT("$count"),
    FIRST("$first"),
    LAST("$last"),
    MIN("$min"),
    MAX("$max"),
    SUM("$sum"),
    AVG("$avg");

    private final String value;
}
