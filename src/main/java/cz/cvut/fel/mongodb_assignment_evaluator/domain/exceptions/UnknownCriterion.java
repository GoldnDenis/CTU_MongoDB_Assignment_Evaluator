package cz.cvut.fel.mongodb_assignment_evaluator.domain.exceptions;

public class UnknownCriterion extends RuntimeException {
    public UnknownCriterion(String name) {
        super("Evaluation logic for criterion='" + name + "' is not defined");
    }
}
