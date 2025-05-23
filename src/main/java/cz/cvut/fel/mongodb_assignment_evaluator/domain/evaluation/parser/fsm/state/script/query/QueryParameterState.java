package cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.parser.fsm.state.script.query;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.parser.ScriptParser;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.parser.fsm.state.ParserState;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.parser.fsm.state.script.MultiLineCommentState;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.parser.fsm.state.script.SingleLineCommentState;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.parser.fsm.state.script.query.parameter.DocumentParameterState;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.parser.fsm.state.script.query.parameter.FunctionParameterState;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.parser.fsm.state.script.query.parameter.StringParameterState;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.parser.iterator.LineIterator;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.parameter.EmptyParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.utility.StringUtility;

/**
 * A state that identifies parameter type and consumes accumulated values.
 * Also, responsible for tracking the parameter positions and its end.
 * Transitions to: DocumentParameterState, StringParameterState, FunctionParameterState, QueryEndState
 */
public class QueryParameterState extends ParserState {
    private final Boolean isModifier;

    public QueryParameterState(ScriptParser context, ParserState previousState, Boolean isModifier) {
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
            if (isParameterEmpty() && assembler.getParameterCount() == 0) {
                assembler.addParameter(new EmptyParameter(), isModifier);
            }
            if (isModifier) {
                assembler.addModifier();
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
            iterator.next();
            if (!Character.isWhitespace(StringUtility.getLastChar(context.getAccumulatedWord()))) {
                context.accumulate(" ");
            }
        } else if (iterator.hasNext()) {
            context.transition(new FunctionParameterState(context, this, isModifier));
        }
    }

    private boolean isParameterEmpty() {
        String accumulatedWord = context.getRawQuery().trim();
        return accumulatedWord.endsWith(",") ||
                accumulatedWord.endsWith("(");
    }
}