package cz.cvut.fel.mongodb_assignment_evaluator.service.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ResultStates {
    NOT_FULFILLED("Not Fulfilled"),
    PARTLY_FULFILLED("Partly Fulfilled"),
    FULFILLED("Fulfilled");

    private final String text;

    public static ResultStates evaluate(int current, int required) {
        if (current <= 0) {
            return NOT_FULFILLED;
        } else if (current >= required) {
            return FULFILLED;
        } else {
            return PARTLY_FULFILLED;
        }
    }
}
