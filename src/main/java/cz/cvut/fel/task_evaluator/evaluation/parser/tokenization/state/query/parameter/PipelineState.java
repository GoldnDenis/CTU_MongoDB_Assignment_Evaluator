package cz.cvut.fel.task_evaluator.evaluation.parser.tokenization.state.query.parameter;

import cz.cvut.fel.task_evaluator.evaluation.parser.iterator.LineIterator;
import cz.cvut.fel.task_evaluator.evaluation.parser.tokenization.ParserStateMachine;
import cz.cvut.fel.task_evaluator.evaluation.parser.tokenization.state.ParserState;

public class PipelineState extends ParserState {
    public PipelineState(ParserStateMachine context) {
        super(context);
    }

    @Override
    public void process(LineIterator iterator) {

    }
}
