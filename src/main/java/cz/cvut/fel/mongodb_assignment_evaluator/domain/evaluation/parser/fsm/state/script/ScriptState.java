package cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.parser.fsm.state.script;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.parser.ScriptParser;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.parser.fsm.state.ParserState;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.parser.fsm.state.script.query.QueryStartState;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.parser.iterator.LineIterator;

/**
 * A starting point of parsing.
 * A state that represents a waiting hub for new queries.
 * Transitions to: both comment states and QueryStartState, initiating a query accumulation.
 */
public class ScriptState extends ParserState {
    public ScriptState(ScriptParser context) {
        super(context, null);
    }

    @Override
    public void process(LineIterator iterator) {
        if (!canTransition(iterator)) {
            iterator.nextAll();
        } else if (iterator.startsWith("//")) {
            context.transition(new SingleLineCommentState(context, this));
        } else if (iterator.startsWith("/*")) {
            context.transition(new MultiLineCommentState(context, this));
        } else if (iterator.startsWithStringQuote()) {
            iterator.nextStringConstruct();
        } else if (iterator.startsWith("db")) {
            context.initAssembler(iterator.getCurrentIndex() + 1);
            context.accumulate(iterator.nextMatch("db"));
            context.transition(new QueryStartState(context, this));
        } else {
            iterator.next();
        }
    }

    private boolean canTransition(LineIterator iterator) {
        return iterator.contains("//") ||
                iterator.contains("/*") ||
                iterator.contains("db") ||
                iterator.contains(".");
    }
}
