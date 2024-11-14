package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.parser.fsm.state.query;

import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.parser.fsm.ParserStateMachine;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.parser.fsm.state.ParserState;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.parser.fsm.state.ScriptState;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.parser.iterator.LineIterator;

public class QueryEndState extends ParserState {
    public QueryEndState(ParserStateMachine context) {
        super(context);
    }

    @Override
    public void process(LineIterator iterator) {
        iterator.skipWhitespaces();
        if (iterator.startsWith(";")) {
            context.appendToQuery(iterator.next());
            context.saveQuery();
            context.setState(new ScriptState(context));
        } else if (iterator.startsWith(".")) {
            context.setState(new QueryState(context, true));
        } else if (iterator.hasNext()) {
            // todo syntax error
            processSyntaxError("After ')' was expecting '.' or ';'", iterator);
        }
    }
}
