package cz.cvut.fel.task_evaluator.service.evaluation.parser.fsm.state.comment;

import cz.cvut.fel.task_evaluator.service.evaluation.parser.fsm.ParserStateMachine;
import cz.cvut.fel.task_evaluator.service.evaluation.parser.fsm.state.ParserState;
import cz.cvut.fel.task_evaluator.service.evaluation.parser.fsm.state.ScriptState;
import cz.cvut.fel.task_evaluator.service.evaluation.parser.iterator.LineIterator;

public class SingleLineCommentState extends ParserState {

    public SingleLineCommentState(ParserStateMachine context) {
        super(context);
    }

    @Override
    public void process(LineIterator iterator) {
        context.setLastComment(iterator.nextAll());
        context.setLastQueryOperation("");
        context.setState(new ScriptState(context));
    }
}