package cz.cvut.fel.task_evaluator.service.evaluation.parser.fsm.state.comment;

import cz.cvut.fel.task_evaluator.service.evaluation.parser.fsm.ParserStateMachine;
import cz.cvut.fel.task_evaluator.service.evaluation.parser.fsm.state.ParserState;
import cz.cvut.fel.task_evaluator.service.evaluation.parser.fsm.state.ScriptState;
import cz.cvut.fel.task_evaluator.service.evaluation.parser.iterator.LineIterator;

public class MultiLineCommentState extends ParserState {
    public MultiLineCommentState(ParserStateMachine context) {
        super(context);

    }

    @Override
    public void process(LineIterator iterator) {
        valueAccumulator.append(iterator.consumeMatchOrAll("*/"));
        if (valueAccumulator.toString().endsWith("*/")) {
            context.setLastComment(valueAccumulator.toString());
            context.setLastQueryOperation("");
            context.setState(new ScriptState(context));
        }
    }
}