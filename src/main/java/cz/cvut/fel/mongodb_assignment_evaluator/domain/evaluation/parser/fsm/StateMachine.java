package cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.parser.fsm;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.parser.fsm.state.ParserState;

/**
 * A representation of a State Machine model.
 * Keeps track of the current state and accumulates current word.
 */
public abstract class StateMachine {
    protected ParserState currentState;
    protected final StringBuilder wordAccumulator;

    protected StateMachine() {
        this.wordAccumulator = new StringBuilder();
    }

    public void transition(ParserState state) {
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
