package cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.parser.fsm.state.query;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.exceptions.IncorrectParserSyntax;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.parser.ScriptParser;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.parser.fsm.state.ParserState;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.parser.fsm.state.comment.MultiLineCommentState;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.parser.fsm.state.comment.SingleLineCommentState;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.parser.fsm.state.query.parameter.QueryParameterState;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.parser.iterator.LineIterator;
import cz.cvut.fel.mongodb_assignment_evaluator.infrastructure.utility.StringUtility;

/**
 * A state that consumes a collection/operator, then transitions to the parameter state.
 */
public class QueryBodyState extends ParserState {
    private final boolean isModifier;

    public QueryBodyState(ScriptParser context, ParserState previousState, boolean isModifier) {
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
            throw new IncorrectParserSyntax("'system.' prefix isn't allowed. (Reserved for internal use.)");
        } else if (iterator.startsWith(".")) {
            String accumulatedWord = context.getAccumulatedWord();
            if (accumulatedWord.isBlank()) {
                throw new IncorrectParserSyntax("A collection cannot be blank");
            }
            assembler.setCollection(accumulatedWord);
            context.accumulate(iterator.next());
            context.processAccumulatedWord(true);
        } else if (iterator.startsWith("(")) {
            String accumulatedWord = context.getAccumulatedWord();
            if (accumulatedWord.isBlank()) {
                throw new IncorrectParserSyntax("An operator cannot be blank");
            }
            assembler.setOperator(accumulatedWord, isModifier);
            context.accumulate(iterator.next());
            context.processAccumulatedWord(true);
            context.transition(new QueryParameterState(context, this, isModifier));
        } else if (iterator.startsWithWhitespace()) {
            iterator.next();
            if (Character.isWhitespace(StringUtility.getLastChar(context.getAccumulatedWord()))) {
                throw new IncorrectParserSyntax("A collection/operator name cannot contain whitespace in-between");
            }
        } else if (iterator.hasNext()) {
            char c = iterator.next();
            if (!characterIsAllowed(c)) {
                throw new IncorrectParserSyntax("Character '" + c + "' is not allowed in a collection/operator name.");
            }
            context.accumulate(c);
        }
    }

    private boolean characterIsAllowed(char c) {
        return Character.isLetterOrDigit(c) || c == '_';
    }
}
