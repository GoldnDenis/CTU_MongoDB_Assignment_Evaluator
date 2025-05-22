package cz.cvut.fel.mongodb_assignment_evaluator.domain.exceptions;

public class DocumentSyntaxError extends RuntimeException {
    public DocumentSyntaxError(String message) {
        super("Document syntax error caused by " + message);
    }
}
