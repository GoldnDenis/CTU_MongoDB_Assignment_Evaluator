package cz.cvut.fel.task_evaluator.evaluation.parser.fsm.state.query;

import cz.cvut.fel.task_evaluator.evaluation.parser.fsm.ParserStateMachine;
import cz.cvut.fel.task_evaluator.evaluation.parser.fsm.state.ParserState;
import cz.cvut.fel.task_evaluator.evaluation.parser.fsm.state.ParserTransitionState;
import cz.cvut.fel.task_evaluator.evaluation.parser.iterator.LineIterator;

public class StringLiteralState extends ParserState {
    private final Boolean isModifier;

    public StringLiteralState(ParserStateMachine context, Boolean isModifier) {
        super(context);
        this.isModifier = isModifier;
    }

    @Override
    public void process(LineIterator iterator) {
        if (iterator.startsWith(".") || iterator.startsWith("(")) {
            String value = valueAccumulator.toString();
            if (iterator.startsWith(".")) {
                context.setQueryCollection(value);
            } else if (isModifier) {
                context.setModifierOperator(value);
            } else {
                context.setQueryOperator(value);
            }
            context.setState(new QueryState(context, isModifier));
        } else {
            char c = iterator.next();
            if (!Character.isLetterOrDigit(c) && c != '_') {
                // todo syntax error
                context.setState(new ParserTransitionState(context));
            } else {
                valueAccumulator.append(c);
            }
        }
    }
}
