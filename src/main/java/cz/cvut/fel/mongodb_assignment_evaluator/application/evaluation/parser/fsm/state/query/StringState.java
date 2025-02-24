package cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.parser.fsm.state.query;

import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.parser.fsm.ParserStateMachine;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.parser.fsm.state.ParserState;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.parser.fsm.state.query.parameter.QueryParameterState;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.parser.iterator.LineIterator;
import cz.cvut.fel.mongodb_assignment_evaluator.application.model.parameter.StringParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.enums.ParserStates;

public class StringState extends ParserState {
    private final char quote;
    private final String escape;
//    private boolean isModifier;

    public StringState(ParserStateMachine context, ParserState previousState, char quote) {
        super(context, previousState);
        this.quote = quote;
        this.escape = "\\" + quote;
        // .append(quote);
    }

    @Override
    public void process(LineIterator iterator) {
        if (iterator.startsWith(escape)) {
            // .append(iterator.nextMatch(escape));
        } else if (iterator.startsWith(quote)) {
            // .append(quote);
            context.transition(previousState);
        } else if (iterator.hasNext()) {
            // .append(iterator.next());
        } else {
            // todo error
        }
    }
}
