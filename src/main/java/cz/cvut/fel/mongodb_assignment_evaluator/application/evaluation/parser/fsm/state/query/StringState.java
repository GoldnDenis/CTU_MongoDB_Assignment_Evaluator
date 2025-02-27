package cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.parser.fsm.state.query;

import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.parser.fsm.ParserStateMachine;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.parser.fsm.QueryTokenAssembler;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.parser.fsm.state.ParserState;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.parser.fsm.state.ScriptState;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.parser.fsm.state.query.parameter.QueryParameterState;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.parser.iterator.LineIterator;
import cz.cvut.fel.mongodb_assignment_evaluator.application.model.parameter.StringParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.enums.ParserStates;

/**
 * A state that accumulates a string until it encounters a closing quote. It is capable of catching escape sequences.
 * On exit returns back to a previous state. It does not consume an accumulated string, it should be handled in a next state.
 */
public class StringState extends ParserState {
    private final char quote;
    private final String escape;
//    private boolean isModifier;

    public StringState(ParserStateMachine context, ParserState previousState, char quote) {
        super(context, previousState);
        this.quote = quote;
        this.escape = "\\" + quote;

        context.accumulate(quote);
    }

    @Override
    public void process(LineIterator iterator) {
        if (iterator.startsWith(escape)) {
            context.accumulate(iterator.nextMatch(escape));
        } else if (iterator.startsWith(quote)) {
            context.accumulate(iterator.next());
//            Boolean appendFlag = !previousState.getClass().equals(ScriptState.class);
//            context.processAccumulatedWord(appendFlag);
            context.transition(previousState);
        } else if (iterator.hasNext()) {
            context.accumulate(iterator.next());
        } else {
            // todo error
        }
    }
}
