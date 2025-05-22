package cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.parser.fsm.state.script.query.parameter;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.parser.ScriptParser;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.parser.fsm.state.ParserState;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.parser.fsm.state.script.MultiLineCommentState;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.parser.fsm.state.script.SingleLineCommentState;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.parser.fsm.state.script.query.QueryParameterState;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.parser.iterator.LineIterator;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.parameter.FunctionParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.utility.StringUtility;

/**
 * Tracks round parenthesis.
 * State accumulates function, until it closes the last "{".
 * Transitions back to QueryBodyState
 */
public class FunctionParameterState extends ParserState {
    private final Boolean isModifier;
    private int parenthesisCount;

    public FunctionParameterState(ScriptParser context, ParserState previousState, Boolean isModifier) {
        super(context, previousState);
        this.isModifier = isModifier;
        this.parenthesisCount = 1;
    }

    @Override
    public void process(LineIterator iterator) {
        if (iterator.startsWith("//")) {
            context.transition(new SingleLineCommentState(context, this));
        } else if (iterator.startsWith("/*")) {
            context.transition(new MultiLineCommentState(context, this));
        } else {
            if (iterator.startsWith("(")) {
                parenthesisCount++;
            } else if (iterator.startsWith(")")) {
                parenthesisCount--;
            }

            if (parenthesisCount > 0) {
                if (iterator.startsWithStringQuote()) {
                    context.accumulate(iterator.nextStringConstruct());
                } else if (startsWithDoubleWhitespace(iterator)) {
                    iterator.next();
                } else {
                    context.accumulate(iterator.next());
                }
            } else if (!context.getAccumulatedWord().isBlank()) {
                assembler.addParameter(new FunctionParameter(context.getAccumulatedWord()), isModifier);
                context.transition(new QueryParameterState(context, this, isModifier));
            }
        }
    }

    private boolean startsWithDoubleWhitespace(LineIterator iterator) {
        if (!iterator.startsWithWhitespace()) {
            return false;
        }
        char lastChar = StringUtility.getLastChar(context.getAccumulatedWord());
        return Character.isWhitespace(lastChar);
    }
}

