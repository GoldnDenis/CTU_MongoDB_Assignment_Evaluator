package cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.parser.fsm.state.script.query;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.parser.ScriptParser;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.parser.fsm.state.ParserState;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.parser.fsm.state.script.MultiLineCommentState;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.parser.fsm.state.script.SingleLineCommentState;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.parser.iterator.LineIterator;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.exceptions.IncorrectParserSyntax;


/**
 * A transitional state that awaits "." and then goes to QueryBodyState
 * Guarantees that "db" is followed by a dot and allows for comments and whitespaces.
 */
public class QueryStartState extends ParserState {
    public QueryStartState(ScriptParser context, ParserState previousState) {
        super(context, previousState);
    }

    @Override
    public void process(LineIterator iterator) {
        if (iterator.startsWith("//")) {
            context.transition(new SingleLineCommentState(context, this));
        } else if (iterator.startsWith("/*")) {
            context.transition(new MultiLineCommentState(context, this));
        } else if (iterator.startsWith(".")) {
            context.accumulate(iterator.next());
            context.processAccumulatedWord(true);
            context.transition(new QueryBodyState(context, this, false));
        } else if (iterator.startsWithWhitespace()) {
            iterator.next();
        } else {
            throw new IncorrectParserSyntax("Was expecting a dot after 'db'");
        }
    }
}
