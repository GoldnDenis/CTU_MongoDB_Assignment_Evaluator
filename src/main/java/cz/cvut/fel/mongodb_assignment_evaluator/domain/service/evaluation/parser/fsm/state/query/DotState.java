package cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.parser.fsm.state.query;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.exceptions.IncorrectParseSyntax;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.parser.fsm.ParserStateMachine;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.parser.fsm.state.ParserState;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.parser.fsm.state.comment.MultiLineCommentState;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.parser.fsm.state.comment.SingleLineCommentState;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.parser.iterator.LineIterator;


/**
 * A transitional state that should be preceded by consuming "db". Its purpose to guarantee that the substring is followed by a dot.
 */
public class DotState extends ParserState {
    public DotState(ParserStateMachine context, ParserState previousState) {
        super(context, previousState);
    }

    @Override
    public void process(LineIterator iterator) {
        if (iterator.startsWith("//")) {
            context.transition(new SingleLineCommentState(context, this));
        } else if (iterator.startsWith("/*")) {
            context.transition(new MultiLineCommentState(context, this));
        } else if (iterator.startsWith(".")) {
            context.accumulate(iterator.next());
            context.processAccumulatedWord(true);
            context.transition(new QueryBodyState(context, this, false));
        } else if (iterator.startsWithWhitespace()) {
            iterator.next();
        } else {
            throw new IncorrectParseSyntax("Was expecting a dot after 'db'");
        }
    }
}
