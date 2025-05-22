package cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.parser.fsm.state.script.query.parameter;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.parser.ScriptParser;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.parser.fsm.state.ParserState;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.parser.fsm.state.script.MultiLineCommentState;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.parser.fsm.state.script.SingleLineCommentState;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.parser.iterator.LineIterator;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.exceptions.IncorrectParserSyntax;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.parameter.StringParameter;

/**
 * A state accumulates the string parameter. Capable of tracking string quotes and connecting separated strings.
 * Transitions back to QueryBodyState.
 */
public class StringParameterState extends ParserState {
    private final boolean isModifier;

    public StringParameterState(ScriptParser context, ParserState previousState, boolean isModifier) {
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
            context.accumulate(iterator.nextStringConstruct());
        } else if (iterator.hasNext()) {
            throw new IncorrectParserSyntax("Was expecting '+', string quote or a comment");
        }
    }
}
