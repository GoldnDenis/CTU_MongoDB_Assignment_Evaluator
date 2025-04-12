package cz.cvut.fel.mongodb_assignment_evaluator.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ResultStates {
    UNFULFILLED("Unfulfilled"),
    PARTIALLY_FULFILLED("Partially Fulfilled"),
    FULFILLED("Fulfilled");

    private final String text;

    public static ResultStates evaluate(long currentScore, long requiredScore) {
        if (currentScore <= 0) {
            return UNFULFILLED;
        } else if (currentScore >= requiredScore) {
            return FULFILLED;
        } else {
            return PARTIALLY_FULFILLED;
        }
    }
}
