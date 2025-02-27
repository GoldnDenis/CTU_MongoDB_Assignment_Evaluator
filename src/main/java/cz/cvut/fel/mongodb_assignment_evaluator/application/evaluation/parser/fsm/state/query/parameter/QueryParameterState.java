package cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.parser.fsm.state.query.parameter;

import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.StringUtility;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.parser.fsm.ParserStateMachine;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.parser.fsm.state.ParserState;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.parser.fsm.state.comment.MultiLineCommentState;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.parser.fsm.state.comment.SingleLineCommentState;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.parser.fsm.state.query.QueryEndState;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.parser.iterator.LineIterator;
import cz.cvut.fel.mongodb_assignment_evaluator.application.model.parameter.EmptyParameter;

/**
 * A state that identifies parameter type and consumes accumulated values.
 */
public class QueryParameterState extends ParserState {
    private final Boolean isModifier;

    public QueryParameterState(ParserStateMachine context, ParserState previousState, Boolean isModifier) {
        super(context, previousState);
        this.isModifier = isModifier;
    }

    @Override
    public void process(LineIterator iterator) {
        if (iterator.startsWith("//")) {
            context.transition(new SingleLineCommentState(context, this));
        } else if (iterator.startsWith("/*")) {
            context.transition(new MultiLineCommentState(context, this));
        } else if (iterator.startsWith("{")) {
            context.transition(new DocumentParameterState(context, this, isModifier, false));
        } else if (iterator.startsWith("[")) {
            context.accumulate(iterator.next());
            context.processAccumulatedWord(true);
            context.transition(new DocumentParameterState(context, this, isModifier, true));
        } else if (iterator.startsWithStringQuote()) {
            context.transition(new StringParameterState(context, this, isModifier));
        } else if (iterator.startsWith(")")) {
            if (isParameterEmpty()) {
                assembler.addParameter(new EmptyParameter(), isModifier);
            }
            context.accumulate(iterator.next());
            context.processAccumulatedWord(true);
            context.transition(new QueryEndState(context, this));
        } else if (iterator.startsWith(",")) {
            if (isParameterEmpty()) {
                assembler.addParameter(new EmptyParameter(), isModifier);
            }
            context.accumulate(iterator.next());
            context.processAccumulatedWord(true);
        } else if (iterator.startsWithWhitespace()) {
            char nextChar = iterator.next();
            char lastChar = StringUtility.getLastChar(context.getAccumulatedWord());
            if (!Character.isWhitespace(lastChar)) {
                context.accumulate(nextChar);
            }
        } else if (iterator.hasNext()) { //todo
            context.transition(new FunctionParameterState(context, this, isModifier));
        }
    }

    private boolean isParameterEmpty() {
        return StringUtility.isWhitespace(context.getAccumulatedWord());
    }
}