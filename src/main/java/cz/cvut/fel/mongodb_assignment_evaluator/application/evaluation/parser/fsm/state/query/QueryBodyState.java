package cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.parser.fsm.state.query;

import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.StringUtility;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.parser.fsm.ParserStateMachine;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.parser.fsm.QueryTokenAssembler;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.parser.fsm.state.ParserState;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.parser.fsm.state.comment.MultiLineCommentState;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.parser.fsm.state.comment.SingleLineCommentState;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.parser.fsm.state.query.parameter.QueryParameterState;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.parser.iterator.LineIterator;
import cz.cvut.fel.mongodb_assignment_evaluator.enums.ParserStates;

/**
 * A state that consumes a collection/operator, then transitions to the parameter state.
 */
public class QueryBodyState extends ParserState {
    private final boolean isModifier;

    public QueryBodyState(ParserStateMachine context, ParserState previousState, boolean isModifier) {
        super(context, previousState);
        this.isModifier = isModifier;
    }

    @Override
    public void process(LineIterator iterator) {
        if (iterator.startsWithWhitespace()) {
            String accumulatedWord = context.getAccumulatedWord();
            if (!accumulatedWord.isBlank() && !Character.isWhitespace(accumulatedWord.charAt(0))) {
//             todo error processSyntaxError("'system.' prefix isn't allowed. (Reserved for internal use.)", iterator);
            }
            iterator.next();
        } else if (iterator.startsWith("//")) {
            context.transition(new SingleLineCommentState(context, this));
        } else if (iterator.startsWith("/*")) {
            context.transition(new MultiLineCommentState(context, this));
        } else if (iterator.startsWith(".system")) {
            iterator.nextMatch(".system");
//             todo error processSyntaxError("'system.' prefix isn't allowed. (Reserved for internal use.)", iterator);
        } else if (iterator.startsWith(".")) {
            String accumulatedWord = context.getAccumulatedWord();
            if (accumulatedWord.isBlank()) {
                // todo error
            }
            assembler.setCollection(accumulatedWord);
            context.accumulate(iterator.next());
            context.processAccumulatedWord(true);
        } else if (iterator.startsWith("(")) {
            String accumulatedWord = context.getAccumulatedWord();
            if (accumulatedWord.isBlank()) {
                // todo error
            }
            assembler.setOperator(accumulatedWord, isModifier);
            context.accumulate(iterator.next());
            context.processAccumulatedWord(true);
            context.transition(new QueryParameterState(context, this, isModifier));
        } else if (iterator.hasNext() ) {
            char c = iterator.next();
            if (!characterIsAllowed(c)) {
                // todo error processSyntaxError("'" + c + "' шs not allowed in collection, operation names", iterator);
                return;
            }
            context.accumulate(c);
        }
    }

    private boolean characterIsAllowed(char c) {
        return Character.isLetterOrDigit(c) || c == '_';
    }
}
