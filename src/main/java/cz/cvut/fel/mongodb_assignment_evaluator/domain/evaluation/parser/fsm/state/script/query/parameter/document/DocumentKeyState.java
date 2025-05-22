package cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.parser.fsm.state.script.query.parameter.document;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.parser.ScriptParser;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.parser.fsm.state.ParserState;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.parser.fsm.state.script.MultiLineCommentState;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.parser.fsm.state.script.SingleLineCommentState;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.parser.iterator.LineIterator;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.utility.StringUtility;

/**
 * State that consumes document key values and transitions to DocumentValueState after ":".
 * Upon receiving "}" either returns back to DocumentParameterState meaning that all brackets were closed,
 * otherwise to DocumentValueState and closes the nested document.
 */
public class DocumentKeyState extends ParserState {
    public DocumentKeyState(ScriptParser context, ParserState previousState) {
        super(context, previousState);
    }

    @Override
    public void process(LineIterator iterator) {
        if (iterator.startsWithStringQuote()) {
            String next = iterator.nextStringConstruct();
            context.appendToRawQuery(next);
            context.accumulate(next);
        } else if (iterator.startsWith("//")) {
            context.transition(new SingleLineCommentState(context, this));
        } else if (iterator.startsWith("/*")) {
            context.transition(new MultiLineCommentState(context, this));
        } else if (iterator.startsWith(":")) {
            char next = iterator.next();
            context.accumulate(next);
            context.appendToRawQuery(String.valueOf(next));
            context.transition(new DocumentValueState(context, this));
        } else if (iterator.startsWith("}")) {
            if (previousState.getClass().equals(DocumentValueState.class)) {
                char next = iterator.next();
                context.accumulate(next);
                context.appendToRawQuery(String.valueOf(next));
            }
            context.transition(previousState);
        } else if (iterator.startsWithWhitespace()) {
            iterator.next();
            if (!Character.isWhitespace(StringUtility.getLastChar(context.getAccumulatedWord()))) {
                context.accumulate(" ");
                context.appendToRawQuery(" ");
            }
        } else if (iterator.hasNext()) {
            char next = iterator.next();
            context.accumulate(next);
            context.appendToRawQuery(String.valueOf(next));
        }
    }
}
