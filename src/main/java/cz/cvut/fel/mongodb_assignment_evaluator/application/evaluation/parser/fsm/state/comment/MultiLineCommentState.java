package cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.parser.fsm.state.comment;

import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.parser.fsm.ParserStateMachine;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.parser.fsm.state.ParserState;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.parser.fsm.state.ScriptState;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.parser.iterator.LineIterator;
import cz.cvut.fel.mongodb_assignment_evaluator.enums.ParserStates;

public class MultiLineCommentState extends ParserState {
    public MultiLineCommentState(ParserStateMachine context, ParserState previousState) {
        super(context, previousState);
    }

    @Override
    public void process(LineIterator iterator) {
        if (iterator.contains("*/")) {
            context.appendToComment(iterator.nextMatch("*/"));
            context.setLastQueryOperation("");
            context.transition(previousState);
        } else {
            context.appendToComment(iterator.nextAll());
        }
    }
}