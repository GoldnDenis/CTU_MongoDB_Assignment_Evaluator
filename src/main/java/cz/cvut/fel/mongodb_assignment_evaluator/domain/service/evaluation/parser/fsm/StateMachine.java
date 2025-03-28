package cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.parser.fsm;

public interface StateMachine<S> {
    void transition(S state);
}
