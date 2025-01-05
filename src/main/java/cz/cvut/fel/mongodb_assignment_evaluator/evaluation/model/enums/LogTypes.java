package cz.cvut.fel.mongodb_assignment_evaluator.evaluation.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.logging.Level;

@AllArgsConstructor
@Getter
public enum LogTypes {
    READER("While Reading."),
    PARSER("While Parsing."),
    CHECKER("While Checking criteria."),;

    private final String message;
}
