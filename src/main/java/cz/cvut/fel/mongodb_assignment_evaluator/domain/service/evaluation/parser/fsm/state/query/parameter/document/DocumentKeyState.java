package cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.parser.fsm.state.query.parameter.document;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.parser.ScriptParser;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.parser.fsm.state.ParserState;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.parser.fsm.state.comment.MultiLineCommentState;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.parser.fsm.state.comment.SingleLineCommentState;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.parser.iterator.LineIterator;
import cz.cvut.fel.mongodb_assignment_evaluator.infrastructure.utility.StringUtility;

public class DocumentKeyState extends ParserState {
    public DocumentKeyState(ScriptParser context, ParserState previousState) {
        super(context, previousState);
    }

    @Override
    public void process(LineIterator iterator) {
        if (iterator.startsWithStringQuote()) {
            context.accumulate(iterator.nextStringConstruct());
        } else if (iterator.startsWith("//")) {
            context.transition(new SingleLineCommentState(context, this));
        } else if (iterator.startsWith("/*")) {
            context.transition(new MultiLineCommentState(context, this));
        } else if (iterator.startsWith(":")) {
            context.accumulate(iterator.next());
            context.transition(new DocumentValueState(context, this));
        } else if (iterator.startsWith("}")) {
            if (previousState.getClass().equals(DocumentValueState.class)) {
                context.accumulate(iterator.next());
            }
            // todo json saving format, i.e. new lines, tabs, spaces, etc.
            context.transition(previousState);
        } else if (iterator.startsWithWhitespace()) {
            iterator.next();
            if (!Character.isWhitespace(StringUtility.getLastChar(context.getAccumulatedWord()))) {
                context.accumulate(" ");
            }
        } else if (iterator.hasNext()) {
            char c = iterator.next();
            context.accumulate(c);
        }
    }
}
