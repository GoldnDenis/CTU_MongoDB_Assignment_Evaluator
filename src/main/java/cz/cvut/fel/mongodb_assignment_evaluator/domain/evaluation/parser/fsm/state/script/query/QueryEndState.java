package cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.parser.fsm.state.script.query;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.parser.ScriptParser;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.parser.fsm.state.ParserState;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.parser.fsm.state.script.MultiLineCommentState;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.parser.fsm.state.script.ScriptState;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.parser.fsm.state.script.SingleLineCommentState;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.parser.iterator.LineIterator;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.exceptions.IncorrectParserSyntax;

/**
 * A state that awaits either the ending, i.e. ';', or a dot and starts accumulating modifiers.
 * Otherwise, throws an IncorrectParserSyntax exception
 * Transitions to ScriptState, QueryBodyState
 */
public class QueryEndState extends ParserState {
    public QueryEndState(ScriptParser context, ParserState previousState) {
        super(context, previousState);
    }

    @Override
    public void process(LineIterator iterator) {
        if (iterator.startsWith("//")) {
            context.transition(new SingleLineCommentState(context, this));
        } else if (iterator.startsWith("/*")) {
            context.transition(new MultiLineCommentState(context, this));
        } else if (iterator.startsWithWhitespace()) {
            iterator.next();
        } else if (iterator.startsWith(";")) {
            context.accumulate(iterator.next());
            context.processAccumulatedWord(true);
            context.saveQuery();
            context.transition(new ScriptState(context));
        } else if (iterator.startsWith(".")) {
            context.accumulate(iterator.next());
            context.processAccumulatedWord(true);
            context.transition(new QueryBodyState(context, this, true));
        } else if (iterator.hasNext()) {
            throw new IncorrectParserSyntax("Expected a semicolon (;) or the start of a modifier (.) at the end of the query");
        }
    }
}
