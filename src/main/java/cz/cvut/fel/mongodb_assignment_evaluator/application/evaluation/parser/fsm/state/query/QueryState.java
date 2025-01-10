package cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.parser.fsm.state.query;

import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.parser.fsm.ParserStateMachine;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.parser.fsm.state.ParserState;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.parser.fsm.state.query.parameter.ParameterState;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.parser.iterator.LineIterator;

public class QueryState extends ParserState {
    private boolean isModifier;

    public QueryState(ParserStateMachine context, Boolean isModifier) {
        super(context);
        this.isModifier = isModifier;
    }

    @Override
    public void process(LineIterator iterator) {
        iterator.skipWhitespaces();

        if (iterator.startsWith(".system.")) {
            // todo syntax error
            processSyntaxError("'.system.' isn't allowed in collection or operation", iterator);
        }  else if (iterator.startsWith("//")) {
            iterator.nextAll();
        } else if (iterator.startsWith(".")) {
            context.appendToQuery(iterator.next());
            context.setState(new StringLiteralState(context, isModifier));
        } else if (iterator.startsWith("(")) {
            context.setState(new ParameterState(context, isModifier));
        } else if (iterator.startsWith(")")) {
            if (isModifier) {
                context.saveModifier();
            }
            context.appendToQuery(iterator.next());
            context.setState(new QueryEndState(context));
        } else if (iterator.hasNext()) {
            // todo syntax error
            processSyntaxError("Invalid query syntax. Expected '.', '(' or ')'", iterator);
        }
    }
}
