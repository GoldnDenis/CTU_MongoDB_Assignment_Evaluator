package cz.cvut.fel.mongodb_assignment_evaluator.domain.exceptions;

public class IncorrectParseSyntax extends RuntimeException {
    public IncorrectParseSyntax(String message) {
        super(message);
    }
}
