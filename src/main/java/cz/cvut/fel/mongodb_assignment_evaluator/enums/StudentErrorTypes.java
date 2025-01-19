package cz.cvut.fel.mongodb_assignment_evaluator.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum StudentErrorTypes {
    READER("While Reading."),
    PARSER("While Parsing."),
    CHECKER("While Checking criteria."),;

    private final String message;
}
