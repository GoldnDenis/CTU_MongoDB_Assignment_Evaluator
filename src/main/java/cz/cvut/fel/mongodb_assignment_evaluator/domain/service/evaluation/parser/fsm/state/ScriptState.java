package cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.parser.fsm.state;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.parser.fsm.ParserStateMachine;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.parser.fsm.state.comment.MultiLineCommentState;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.parser.fsm.state.comment.SingleLineCommentState;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.parser.fsm.state.query.DotState;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.parser.fsm.state.query.StringState;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.parser.iterator.LineIterator;

/**
 * A state that represents a starting point. Its purpose to accumulate comments and initiate a query token accumulation.
 */
public class ScriptState extends ParserState {
    public ScriptState(ParserStateMachine context) {
        super(context, null);
    }

    @Override
    public void process(LineIterator iterator) {
        if (!canTransition(iterator)) {
            iterator.nextAll();
        } else if (iterator.startsWith("//")) {
            context.transition(new SingleLineCommentState(context, this));
        } else if (iterator.startsWith("/*")) {
            context.transition(new MultiLineCommentState(context, this));
        } else if (iterator.startsWithStringQuote()) {
            context.transition(new StringState(context, this, iterator.next()));
            context.processAccumulatedWord(false);
        } else if (iterator.startsWith("db")) {
            context.initAssembler(iterator.getCurrentIndex() + 1);
            context.accumulate(iterator.nextMatch("db"));
            context.transition(new DotState(context, this));
        } else {
            iterator.next();
        }
    }

    private boolean canTransition(LineIterator iterator) {
        return iterator.contains("//") ||
                iterator.contains("/*") ||
                iterator.contains("db") ||
                iterator.contains(".");
    }
}
