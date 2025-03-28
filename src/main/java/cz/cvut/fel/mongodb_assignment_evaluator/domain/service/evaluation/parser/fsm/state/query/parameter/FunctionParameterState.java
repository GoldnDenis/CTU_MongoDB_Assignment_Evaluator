package cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.parser.fsm.state.query.parameter;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.parameter.FunctionParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.parser.fsm.ParserStateMachine;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.parser.fsm.state.ParserState;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.parser.fsm.state.query.StringState;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.parser.iterator.LineIterator;
import cz.cvut.fel.mongodb_assignment_evaluator.infrastructure.utility.StringUtility;

public class FunctionParameterState extends ParserState {
    private final Boolean isModifier;
    private int parenthesisCount;

    public FunctionParameterState(ParserStateMachine context, ParserState previousState, Boolean isModifier) {
        super(context, previousState);
        this.isModifier = isModifier;
        this.parenthesisCount = 1;
    }

    @Override
    public void process(LineIterator iterator) {
        if (iterator.startsWith("(")) {
            parenthesisCount++;
        } else if (iterator.startsWith(")")) {
            parenthesisCount--;
        }

        if (parenthesisCount > 0) {
            if (iterator.startsWithStringQuote()) {
                context.transition(new StringState(context, this, iterator.next()));
            } else if (startsWithDoubleWhitespace(iterator)) {
                iterator.next();
            } else {
                context.accumulate(iterator.next());
            }
        } else if (!context.getAccumulatedWord().isBlank()) {
            assembler.addParameter(new FunctionParameter(context.getAccumulatedWord()), isModifier);
            context.transition(new QueryParameterState(context, this, isModifier));
        }
    }

    private boolean startsWithDoubleWhitespace(LineIterator iterator) {
        if (!iterator.startsWithWhitespace()) {
            return false;
        }
        char lastChar = StringUtility.getLastChar(context.getAccumulatedWord());
        return Character.isWhitespace(lastChar);
    }
}

