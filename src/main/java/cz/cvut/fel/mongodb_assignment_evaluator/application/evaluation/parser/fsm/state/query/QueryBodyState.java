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
import cz.cvut.fel.mongodb_assignment_evaluator.exception.IncorrectParseSyntax;

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
        if (iterator.startsWith("//")) {
            context.transition(new SingleLineCommentState(context, this));
        } else if (iterator.startsWith("/*")) {
            context.transition(new MultiLineCommentState(context, this));
        } else if (iterator.startsWith(".system")) {
            iterator.nextMatch(".system");
            throw new IncorrectParseSyntax("'system.' prefix isn't allowed. (Reserved for internal use.)");
        } else if (iterator.startsWith(".")) {
            String accumulatedWord = context.getAccumulatedWord();
            if (accumulatedWord.isBlank()) {
                throw new IncorrectParseSyntax("A collection cannot be blank");
            }
            assembler.setCollection(accumulatedWord);
            context.accumulate(iterator.next());
            context.processAccumulatedWord(true);
        } else if (iterator.startsWith("(")) {
            String accumulatedWord = context.getAccumulatedWord();
            if (accumulatedWord.isBlank()) {
                throw new IncorrectParseSyntax("An operator cannot be blank");
            }
            assembler.setOperator(accumulatedWord, isModifier);
            context.accumulate(iterator.next());
            context.processAccumulatedWord(true);
            context.transition(new QueryParameterState(context, this, isModifier));
        } else if (iterator.startsWithWhitespace()) {
            iterator.next();
        } else if (iterator.hasNext() ) {
            char c = iterator.next();
            String accumulatedWord = context.getAccumulatedWord();
            if (Character.isWhitespace(c) && Character.isWhitespace(StringUtility.getLastChar(accumulatedWord))) {
                throw new IncorrectParseSyntax("A collection/operator name cannot contain whitespace in-between");
            }
            if (!characterIsAllowed(c)) {
                throw new IncorrectParseSyntax("Character '" + c + "' is not allowed in a collection/operator name.");
            }
            context.accumulate(c);
        }
    }

    private boolean characterIsAllowed(char c) {
        return Character.isLetterOrDigit(c) || c == '_';
    }
}
