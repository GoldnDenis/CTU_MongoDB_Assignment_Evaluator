package cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.parser.fsm.state.script;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.parser.ScriptParser;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.parser.fsm.state.ParserState;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.parser.iterator.LineIterator;

/**
 * A state that consumes a whole line as a single-line comment.
 * On exit returns back to a previous state.
 */
public class SingleLineCommentState extends ParserState {
    public SingleLineCommentState(ScriptParser context, ParserState previousState) {
        super(context, previousState);
    }

    @Override
    public void process(LineIterator iterator) {
        assembler.appendComment(iterator.nextAll());
        assembler.setLastQueryOperator("");
        context.transition(previousState);
    }
}