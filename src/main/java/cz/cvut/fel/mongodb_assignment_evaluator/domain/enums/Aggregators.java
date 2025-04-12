package cz.cvut.fel.mongodb_assignment_evaluator.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

@Getter
public enum Aggregators {
    COUNT("$count"),
    MIN_MAX("$min", "$max"),
    SUM_AVG("$sum", "$avg");

    Aggregators(String... aggregators) {
        this.aggregators = new HashSet<>(Set.of(aggregators));
    }

    private final Set<String> aggregators;
}
