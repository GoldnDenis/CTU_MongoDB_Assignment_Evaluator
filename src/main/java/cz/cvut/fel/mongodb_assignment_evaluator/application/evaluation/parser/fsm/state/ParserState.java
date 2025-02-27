package cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.parser.fsm.state;

import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.parser.fsm.ParserStateMachine;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.parser.fsm.QueryTokenAssembler;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.parser.iterator.LineIterator;
import cz.cvut.fel.mongodb_assignment_evaluator.enums.ParserStates;
import lombok.Getter;

public abstract class ParserState {
    protected final ParserStateMachine context;
    protected final ParserState previousState;
    protected final QueryTokenAssembler assembler;
//    protected final StringBuilder valueAccumulator;

    public ParserState(ParserStateMachine context, ParserState previousState) {
        this.context = context;
        this.previousState = previousState;
        this.assembler = context.getAssembler();
//        this.valueAccumulator = new StringBuilder();
    }

    public abstract void process(LineIterator iterator);

//    public abstract void acceptWord(QueryTokenAssembler assembler);

//    public abstract ParserStates getEnumeration();

//    public abstract void process(Character c);

//    protected void processSyntaxError(String message, LineIterator iterator) {
//        int lineNumber = iterator.getRowIndex() + 1;
//        int columnNumber = iterator.getCurrentIndex() + 1;
//        message = "Parsing error: " + message + " at " + lineNumber + ":" + columnNumber;
//        System.err.println(message);
//        context.resetAccumulators();
//        context.setCurrentState(new ScriptState(context));
//    }
}
