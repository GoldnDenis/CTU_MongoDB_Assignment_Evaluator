package cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.parser.fsm.state.comment;

import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.parser.fsm.ParserStateMachine;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.parser.fsm.state.ParserState;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.parser.fsm.state.ScriptState;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.parser.iterator.LineIterator;

public class MultiLineCommentState extends ParserState {
    public MultiLineCommentState(ParserStateMachine context) {
        super(context);

    }

    @Override
    public void process(LineIterator iterator) {
        valueAccumulator.append(iterator.consumeMatchOrAll("*/"));
        if (valueAccumulator.toString().endsWith("*/")) {
            context.appendToComment(valueAccumulator.toString());
            context.setLastQueryOperation("");
            context.setState(new ScriptState(context));
        }
    }
}