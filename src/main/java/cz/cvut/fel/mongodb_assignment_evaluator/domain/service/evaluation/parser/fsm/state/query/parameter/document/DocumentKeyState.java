package cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.parser.fsm.state.query.parameter.document;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.exceptions.IncorrectParserSyntax;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.parser.ScriptParser;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.parser.fsm.state.ParserState;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.parser.fsm.state.query.StringState;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.parser.iterator.LineIterator;
import cz.cvut.fel.mongodb_assignment_evaluator.infrastructure.utility.StringUtility;

public class DocumentKeyState extends ParserState {
    public DocumentKeyState(ScriptParser context, ParserState previousState) {
        super(context, previousState);
    }

    @Override
    public void process(LineIterator iterator) {
        if (iterator.startsWithStringQuote()) {
            context.transition(new StringState(context, this, iterator.next()));
        } else if (iterator.startsWithWhitespace()) {
            iterator.next();
        } else if (iterator.hasNext()) {
            char c = iterator.next();
            String accumulatedWord = context.getAccumulatedWord();
            if (Character.isWhitespace(c) && !Character.isWhitespace(StringUtility.getLastChar(accumulatedWord))) {
                throw new IncorrectParserSyntax("A collection/operator name cannot contain whitespace in-between");
            }
            if (!characterIsAllowed(c)) {
                throw new IncorrectParserSyntax("Character '" + c + "' is not allowed in a collection/operator name.");
            }
            context.accumulate(c);
        }
    }
}
