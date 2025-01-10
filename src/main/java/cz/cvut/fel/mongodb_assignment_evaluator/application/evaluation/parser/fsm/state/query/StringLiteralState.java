package cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.parser.fsm.state.query;

import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.parser.fsm.ParserStateMachine;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.parser.fsm.state.ParserState;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.parser.iterator.LineIterator;

public class StringLiteralState extends ParserState {
    private final Boolean isModifier;

    public StringLiteralState(ParserStateMachine context, Boolean isModifier) {
        super(context);
        this.isModifier = isModifier;
    }

    @Override
    public void process(LineIterator iterator) {
        iterator.skipWhitespaces();

        if (iterator.startsWith(".") || iterator.startsWith("(")) {
            String value = valueAccumulator.toString();

            if (iterator.startsWith(".")) {
                if (value.equals("system")) {
                    // todo syntax error
                    processSyntaxError("'system.' prefix isn't allowed. (Reserved for internal use.)", iterator);
                    return;
                }
                context.setCurrentCollection(value);
            } else if (isModifier) {
                context.getModifierBuilder().setModifier(value);
            } else {
                context.setQueryOperator(value);
            }
            context.appendToQuery(value);
            context.setState(new QueryState(context, isModifier));
        } else if (iterator.hasNext()) {
            char c = iterator.next();
            if (!Character.isLetterOrDigit(c) && c != '_') {
                //todo syntax error
//                if (c == '$') {
//                    processSyntaxError("'$' шs not allowed in collection, operation names", iterator);
//                }
                processSyntaxError("'" + c + "' шs not allowed in collection, operation names", iterator);
                return;
            }
            valueAccumulator.append(c);
        }
    }
}
