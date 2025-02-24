package cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.parser.fsm;

public interface StateMachine<S> {
    void transition(S state);
    void acceptWord();
}
