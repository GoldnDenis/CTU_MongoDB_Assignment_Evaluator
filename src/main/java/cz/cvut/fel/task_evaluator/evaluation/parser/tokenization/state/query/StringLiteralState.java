package cz.cvut.fel.task_evaluator.evaluation.parser.tokenization.state.query;

import cz.cvut.fel.task_evaluator.evaluation.parser.iterator.LineIterator;
import cz.cvut.fel.task_evaluator.evaluation.parser.tokenization.ParserStateMachine;
import cz.cvut.fel.task_evaluator.evaluation.parser.tokenization.state.ParserState;
import cz.cvut.fel.task_evaluator.evaluation.parser.tokenization.state.transition.ParserTransitionState;

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
