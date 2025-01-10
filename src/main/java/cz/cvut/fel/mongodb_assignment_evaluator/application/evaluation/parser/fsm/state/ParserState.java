package cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.parser.fsm.state;

import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.parser.fsm.ParserStateMachine;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.parser.iterator.LineIterator;

public abstract class ParserState {
    protected final ParserStateMachine context;
    protected final StringBuilder valueAccumulator;

    public ParserState(ParserStateMachine context) {
        this.context = context;
        this.valueAccumulator = new StringBuilder();
    }

    public abstract void process(LineIterator iterator);

    protected void processSyntaxError(String message, LineIterator iterator) {
        int lineNumber = iterator.getRowIndex() + 1;
        int columnNumber = iterator.getCurrentColumnIndex() + 1;
        message = "Parsing error: " + message + " at " + lineNumber + ":" + columnNumber;
        System.err.println(message);
        context.resetAccumulators();
        context.setState(new ScriptState(context));
    }
}
