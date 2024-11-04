package cz.cvut.fel.task_evaluator.evaluation.parser.tokenization.state;

import cz.cvut.fel.task_evaluator.evaluation.parser.iterator.LineIterator;
import cz.cvut.fel.task_evaluator.evaluation.parser.tokenization.ParserStateMachine;

public abstract class ParserState {
    protected final ParserStateMachine context;

    protected final StringBuilder valueAccumulator;

    public ParserState(ParserStateMachine context) {
        this.context = context;

        this.valueAccumulator = new StringBuilder();
    }

    public abstract void process(LineIterator iterator);

    protected void setQueryPosition(LineIterator iterator) {
        int lineNumber = iterator.getRowIndex() + 1;
        int columnNumber = iterator.getCurrentColumnIndex() + 1;
        context.setQueryPosition(lineNumber, columnNumber);
    }
}
