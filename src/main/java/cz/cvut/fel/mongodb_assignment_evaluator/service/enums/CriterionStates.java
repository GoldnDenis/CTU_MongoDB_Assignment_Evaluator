package cz.cvut.fel.mongodb_assignment_evaluator.service.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CriterionStates {
    NOT_FULFILLED("Not Fulfilled"),
    PARTLY_FULFILLED("Partly Fulfilled"),
    FULFILLED("Fulfilled");

    private final String state;
}
