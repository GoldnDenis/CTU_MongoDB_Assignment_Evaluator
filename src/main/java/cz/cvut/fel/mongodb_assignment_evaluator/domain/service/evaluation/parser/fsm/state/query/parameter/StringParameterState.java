package cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.parser.fsm.state.query.parameter;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.exceptions.IncorrectParseSyntax;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.parameter.StringParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.parser.fsm.ParserStateMachine;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.parser.fsm.state.ParserState;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.parser.fsm.state.comment.MultiLineCommentState;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.parser.fsm.state.comment.SingleLineCommentState;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.parser.fsm.state.query.StringState;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.parser.iterator.LineIterator;

public class StringParameterState extends ParserState {
    private final boolean isModifier;

    public StringParameterState(ParserStateMachine context, ParserState previousState, boolean isModifier) {
        super(context, previousState);
        this.isModifier = isModifier;
    }

    @Override
    public void process(LineIterator iterator) {
        if (iterator.startsWith(",") || iterator.startsWith(")")) {
            assembler.addParameter(new StringParameter(context.getAccumulatedWord()), isModifier);
            context.transition(previousState);
        } else if (iterator.startsWith("//")) {
            context.transition(new SingleLineCommentState(context, this));
        } else if (iterator.startsWith("/*")) {
            context.transition(new MultiLineCommentState(context, this));
        } else if (iterator.startsWith("+") || iterator.startsWithWhitespace()) {
            iterator.next();
        } else if (iterator.startsWithStringQuote()) {
            context.transition(new StringState(context, this, iterator.next()));
        } else if (iterator.hasNext()) {
            throw new IncorrectParseSyntax("Was expecting '+', string quote or a comment");
        }
    }
}
