package cz.cvut.fel.task_evaluator.service.evaluation.parser.fsm.state.query.parameter;

import cz.cvut.fel.task_evaluator.service.evaluation.parser.fsm.ParserStateMachine;
import cz.cvut.fel.task_evaluator.service.evaluation.parser.fsm.state.ParserState;
import cz.cvut.fel.task_evaluator.service.evaluation.parser.iterator.LineIterator;
import cz.cvut.fel.task_evaluator.service.evaluation.query.parameter.FunctionParameter;

public class FunctionState extends ParserState {
    private Boolean isModifier;
    private int parenthesisCount;

    public FunctionState(ParserStateMachine context, Boolean isModifier) {
        super(context);
        this.isModifier = isModifier;
        this.parenthesisCount = 1;
    }

    @Override
    public void process(LineIterator iterator) {
        if (iterator.startsWith("(")) {
            parenthesisCount++;
        } else if (iterator.startsWith(")")) {
            parenthesisCount--;
        }

//        char c = iterator.next();
//        if (Character.isWhitespace(c) && valueAccumulator.toString().endsWith(" ")) {
//            iterator.skipWhitespaces();
//            return;
//        }

        if (parenthesisCount > 0) {
            if (iterator.startsWithStringConstruct()) {
                String value = iterator.nextStringConstruct();
                valueAccumulator.append(value);
            } else if (Character.isWhitespace(iterator.peek()) && valueAccumulator.toString().endsWith(" ")) {
                iterator.skipWhitespaces();
            } else {
                valueAccumulator.append(iterator.next());
            }
        } else if (!valueAccumulator.isEmpty()) {
            String value = valueAccumulator.toString();
            context.appendToQuery(value);

            context.addParameter(new FunctionParameter(value), isModifier);

            context.setState(new ParameterState(context, isModifier));
        }
    }
}
