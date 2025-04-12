package cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.parser.fsm;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.parser.fsm.state.ParserState;

public abstract class StateMachine<S> {
    protected S currentState;
    protected final StringBuilder wordAccumulator;

    protected StateMachine() {
        this.wordAccumulator = new StringBuilder();
    }

    public void transition(S state) {
        currentState = state;
    }

    public String getAccumulatedWord() {
        return wordAccumulator.toString();
    }

    public void accumulate(String subWord) {
        wordAccumulator.append(subWord);
    }

    public void accumulate(Character character) {
        wordAccumulator.append(character);
    }
}
