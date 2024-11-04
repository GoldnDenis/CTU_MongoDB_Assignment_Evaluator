package cz.cvut.fel.task_evaluator.evaluation.parser.tokenization.state.comment;

import cz.cvut.fel.task_evaluator.evaluation.parser.iterator.LineIterator;
import cz.cvut.fel.task_evaluator.evaluation.parser.tokenization.ParserStateMachine;
import cz.cvut.fel.task_evaluator.evaluation.parser.tokenization.state.ParserState;
import cz.cvut.fel.task_evaluator.evaluation.parser.tokenization.state.transition.ParserTransitionState;

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
            context.setState(new ParserTransitionState(context));
        }
    }
}
