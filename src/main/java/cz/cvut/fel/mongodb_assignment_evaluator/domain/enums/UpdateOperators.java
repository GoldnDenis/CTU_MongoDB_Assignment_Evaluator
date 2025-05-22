package cz.cvut.fel.mongodb_assignment_evaluator.domain.enums;

import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

@Getter
public enum UpdateOperators {
    UPDATE("$set", "$rename", "$mul", "$inc", "$min", "$max", "$currentDate"),
    FORMULA("$mul", "$inc"),
    ADD("$set", "$mul", "$inc"),
    REMOVE("$unset"),
    ARRAY("$push", "$pull", "$pop", "$pullAll", "$addToSet");

    UpdateOperators(String... operations) {
        this.operations = new HashSet<>(Set.of(operations));
    }

    private final Set<String> operations;
}
