package cz.cvut.fel.mongodb_assignment_evaluator.domain.exceptions;

public class ScriptNotRead extends RuntimeException {
    public ScriptNotRead() {
        super("Was not able to read any scripts");
    }
}
