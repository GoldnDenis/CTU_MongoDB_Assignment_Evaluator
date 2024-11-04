package cz.cvut.fel.task_evaluator.evaluation.parser.tokenization.state.comment;

import cz.cvut.fel.task_evaluator.evaluation.parser.iterator.LineIterator;
import cz.cvut.fel.task_evaluator.evaluation.parser.tokenization.ParserStateMachine;
import cz.cvut.fel.task_evaluator.evaluation.parser.tokenization.state.ParserState;
import cz.cvut.fel.task_evaluator.evaluation.parser.tokenization.state.transition.ParserTransitionState;

public class SingleLineCommentState extends ParserState {

    public SingleLineCommentState(ParserStateMachine context) {
        super(context);
    }

    @Override
    public void process(LineIterator iterator) {
        context.setLastComment(iterator.nextAll());
        context.setLastQueryOperation("");
        context.setState(new ParserTransitionState(context));
    }
}
