package cz.cvut.fel.mongodb_assignment_evaluator.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Set;

@AllArgsConstructor
@Getter
public enum UpdateGroups {
    UPDATE(java.util.Set.of("$set", "$rename", "$mul", "$inc","$min", "$max", "$currentDate")),
    FORMULA(java.util.Set.of("$mul", "$inc")),
    ADD(java.util.Set.of("$set", "$mul", "$inc")),
    REMOVE(java.util.Set.of("$unset")),
    ARRAY_UPDATE(java.util.Set.of("$push")),
    ARRAY_REMOVE(java.util.Set.of("$pull", "$pop"));

    private final Set<String> operations;
}
