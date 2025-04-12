package cz.cvut.fel.mongodb_assignment_evaluator.domain.exceptions;

public class IncorrectParserSyntax extends RuntimeException {
    public IncorrectParserSyntax(String message) {
        super(message);
    }
}
