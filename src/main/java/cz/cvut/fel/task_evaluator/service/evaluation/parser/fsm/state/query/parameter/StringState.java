package cz.cvut.fel.task_evaluator.service.evaluation.parser.fsm.state.query.parameter;


import cz.cvut.fel.task_evaluator.service.evaluation.parser.fsm.ParserStateMachine;
import cz.cvut.fel.task_evaluator.service.evaluation.parser.fsm.state.ParserState;
import cz.cvut.fel.task_evaluator.service.evaluation.parser.iterator.LineIterator;
import cz.cvut.fel.task_evaluator.service.evaluation.query.parameter.StringParameter;

public class StringState extends ParserState {
    private boolean isModifier;

    public StringState(ParserStateMachine context, Boolean isModifier) {
        super(context);
        this.isModifier = isModifier;
    }

    @Override
    public void process(LineIterator iterator) {
        iterator.skipWhitespaces();
        if (iterator.startsWith(",") || iterator.startsWith(")")) {
            String value = valueAccumulator.toString();
            context.addParameter(new StringParameter(value), isModifier);
            context.setState(new ParameterState(context, isModifier));
        } else if (iterator.startsWith("+")) {
            iterator.next();
        } else if (iterator.startsWithStringConstruct()) {
            String value = iterator.nextStringConstruct();
            if (!valueAccumulator.isEmpty()) {
                valueAccumulator.append(" ");
                context.appendToQuery(" ");
            }
            context.appendToQuery(value);
            value = value.substring(1, value.length() - 1);
            valueAccumulator.append(value);
        } else if (iterator.hasNext()) {
            // todo syntax error
            processSyntaxError("invalid character '" + iterator.peek() + "'", iterator);
        }
    }
}
