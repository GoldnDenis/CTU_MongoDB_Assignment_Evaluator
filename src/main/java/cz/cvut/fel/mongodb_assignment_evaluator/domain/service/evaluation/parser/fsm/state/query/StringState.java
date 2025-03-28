package cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.parser.fsm.state.query;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.exceptions.IncorrectParseSyntax;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.parser.fsm.ParserStateMachine;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.parser.fsm.state.ParserState;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.parser.iterator.LineIterator;

/**
 * A state that accumulates a string until it encounters a closing quote. It is capable of catching escape sequences.
 * On exit returns back to a previous state. It does not consume an accumulated string, it should be handled in a next state.
 */
public class StringState extends ParserState {
    private final char quote;
    private final String escape;

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
            context.transition(previousState);
        } else if (iterator.hasNext()) {
            context.accumulate(iterator.next());
        } else {
            throw new IncorrectParseSyntax("Was expecting a closing string quote ('" + quote + "')");
        }
    }
}
