package cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.parser.fsm.state.query;

import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.parser.fsm.ParserStateMachine;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.parser.fsm.state.ParserState;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.parser.fsm.state.comment.MultiLineCommentState;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.parser.fsm.state.comment.SingleLineCommentState;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.parser.iterator.LineIterator;
import cz.cvut.fel.mongodb_assignment_evaluator.enums.ParserStates;

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
            iterator.next();
            context.transition(new QueryBodyState(context, this, false));
        } else if (iterator.startsWithWhitespace()) {
            iterator.next();
        } else {
            // todo error
        }
    }
}
