package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.parser.fsm.state;

import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.parser.fsm.ParserStateMachine;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.parser.fsm.state.comment.MultiLineCommentState;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.parser.fsm.state.comment.SingleLineCommentState;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.parser.fsm.state.query.QueryState;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.parser.iterator.LineIterator;

public class ScriptState extends ParserState {

    public ScriptState(ParserStateMachine context) {
        super(context);
    }

    @Override
    public void process(LineIterator iterator) {
        if (!iterator.contains("//")
                && !iterator.contains("/*")
                && !iterator.contains("db.")) {
            iterator.nextAll();
        } else if (iterator.startsWith("//")) {
            context.setState(new SingleLineCommentState(context));
        } else if (iterator.startsWith("/*")) {
            context.setState(new MultiLineCommentState(context));
        } else if (iterator.startsWith("db.")) {
            context.resetQueryBuilder();
            setQueryPosition(iterator);
            context.appendToQuery(iterator.consumeMatch("db"));
            context.setState(new QueryState(context, false));
        } else if (iterator.startsWithStringConstruct()) {
            iterator.nextStringConstruct();
        } else {
            iterator.next();
        }
    }
}
