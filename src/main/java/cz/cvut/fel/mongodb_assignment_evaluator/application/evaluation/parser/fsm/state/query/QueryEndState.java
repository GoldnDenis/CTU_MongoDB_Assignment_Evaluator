package cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.parser.fsm.state.query;

import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.parser.fsm.ParserStateMachine;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.parser.fsm.state.ParserState;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.parser.fsm.state.ScriptState;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.parser.fsm.state.comment.MultiLineCommentState;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.parser.fsm.state.comment.SingleLineCommentState;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.parser.iterator.LineIterator;

public class QueryEndState extends ParserState {
    public QueryEndState(ParserStateMachine context) {
        super(context);
    }

    @Override
    public void process(LineIterator iterator) {
//        iterator.skipWhitespaces();
        if (iterator.startsWith("//")) {
            context.transition(new SingleLineCommentState(context, this));
        } else if (iterator.startsWith("/*")) {
            context.transition(new MultiLineCommentState(context, this));
        } else if (iterator.startsWithWhitespace()) {
            iterator.next();
        } else if (iterator.startsWith(";")) {
//            context.appendToQuery(iterator.next());
            iterator.next();
            context.saveQuery();
            context.setCurrentState(new ScriptState(context));
        } else if (iterator.startsWith(".")) {
            iterator.next();
            context.setCurrentState(new QueryBodyState(context, getEnumeration(), true));
        } else if (iterator.hasNext()) {
            processSyntaxError("Was expecting '.' or ';' after ')'", iterator);
        }
    }
}
