package cz.cvut.fel.mongodb_assignment_evaluator.domain.exceptions;

public class SourceDirectoryNotFound extends RuntimeException {
    public SourceDirectoryNotFound(String sourceFolder) {
        super("The submissions directory was not found ('" + sourceFolder + "')");
    }
}
