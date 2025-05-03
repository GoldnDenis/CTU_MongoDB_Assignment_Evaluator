package cz.cvut.fel.mongodb_assignment_evaluator.domain.exceptions;

public class CriteriaNotLoaded extends RuntimeException {
    public CriteriaNotLoaded() {
        super("No criteria were loaded for evaluation.");
    }
}
