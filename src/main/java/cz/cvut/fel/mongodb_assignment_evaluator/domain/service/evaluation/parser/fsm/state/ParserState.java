package cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.parser.fsm.state;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.QueryTokenAssembler;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.parser.ScriptParser;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.parser.iterator.LineIterator;

public abstract class ParserState {
    protected final ScriptParser context;
    protected final ParserState previousState;
    protected final QueryTokenAssembler assembler;

    public ParserState(ScriptParser context, ParserState previousState) {
        this.context = context;
        this.previousState = previousState;
        this.assembler = context.getAssembler();
    }

    public abstract void process(LineIterator iterator);
}
