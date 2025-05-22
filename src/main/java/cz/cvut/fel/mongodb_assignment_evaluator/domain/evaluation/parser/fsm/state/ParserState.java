package cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.parser.fsm.state;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.parser.ScriptParser;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.parser.iterator.LineIterator;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.QueryAssembler;
import lombok.Getter;

/**
 * A general representation of the State in the State Machine.
 * In contrast with typical implementation, has a memory of a previous state to return back.
 */
public abstract class ParserState {
    protected final ScriptParser context;
    @Getter
    protected final ParserState previousState;
    protected final QueryAssembler assembler;

    public ParserState(ScriptParser context, ParserState previousState) {
        this.context = context;
        this.previousState = previousState;
        this.assembler = context.getAssembler();
    }

    /**
     * Encapsulates the processing logic of the specific state
     * @param iterator its contents are being processes in the state
     */
    public abstract void process(LineIterator iterator);
}
