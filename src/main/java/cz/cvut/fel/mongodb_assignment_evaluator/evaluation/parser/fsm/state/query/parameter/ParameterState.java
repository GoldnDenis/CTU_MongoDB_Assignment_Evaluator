package cz.cvut.fel.mongodb_assignment_evaluator.evaluation.parser.fsm.state.query.parameter;

import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.parser.fsm.ParserStateMachine;
import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.parser.fsm.state.ParserState;
import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.parser.fsm.state.query.QueryState;
import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.parser.iterator.LineIterator;
import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.model.parameter.EmptyParameter;

public class ParameterState extends ParserState {
    private Boolean isModifier;

    public ParameterState(ParserStateMachine context, Boolean isModifier) {
        super(context);
        this.isModifier = isModifier;
    }

    @Override
    public void process(LineIterator iterator) {
        if (iterator.startsWith("(") || iterator.startsWith(",")) {
            context.appendToQuery(iterator.next());
            iterator.skipWhitespaces();
            if (iterator.startsWith(")") || iterator.startsWith(",")) {
                context.addParameter(new EmptyParameter(), isModifier);
            }
        }
        iterator.skipWhitespaces();

        if (iterator.startsWith("{")) {
            context.setState(new DocumentParameterState(context, isModifier, false));
        }  else if (iterator.startsWith("//")) {
            iterator.nextAll();
        } else if (iterator.startsWith("[")) {
            context.appendToQuery(iterator.next());
            context.setState(new DocumentParameterState(context, isModifier, true));
        } else if (iterator.startsWith(")")) {
            context.setState(new QueryState(context, isModifier));
        } else if (iterator.startsWithStringConstruct()) {
            context.setState(new StringParameterState(context, isModifier));
        } else if (iterator.hasNext()) {
            context.setState(new FunctionParameterState(context, isModifier));
//            context.setState(new StringLiteralState(context, isModifier));
        }
    }
}