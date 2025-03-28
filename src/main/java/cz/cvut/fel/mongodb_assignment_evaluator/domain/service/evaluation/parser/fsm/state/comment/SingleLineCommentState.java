package cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.parser.fsm.state.comment;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.parser.fsm.ParserStateMachine;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.parser.fsm.state.ParserState;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.parser.iterator.LineIterator;

/**
 * A state that consumes a whole line as a single-line comment.
 * On exit returns back to a previous state.
 */
public class SingleLineCommentState extends ParserState {
    public SingleLineCommentState(ParserStateMachine context, ParserState previousState) {
        super(context, previousState);
    }

    @Override
    public void process(LineIterator iterator) {
        assembler.appendComment(iterator.nextAll());
        assembler.setLastQueryOperator("");
        context.transition(previousState);
    }
}