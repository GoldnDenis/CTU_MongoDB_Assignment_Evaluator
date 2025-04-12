package cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.parser.fsm.state.comment;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.parser.ScriptParser;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.parser.fsm.ParserStateMachine;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.parser.fsm.state.ParserState;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.parser.iterator.LineIterator;

/**
 * A state that consumes a multi-line comment. It expects a closing sequence to exit.
 * On exit returns back to a previous state.
 */
public class MultiLineCommentState extends ParserState {
    public MultiLineCommentState(ScriptParser context, ParserState previousState) {
        super(context, previousState);
    }

    @Override
    public void process(LineIterator iterator) {
        if (iterator.contains("*/")) {
            assembler.appendComment(iterator.nextMatch("*/"));
            assembler.setLastQueryOperator("");
            context.transition(previousState);
        } else {
            assembler.appendComment(iterator.nextAll());
        }
    }
}