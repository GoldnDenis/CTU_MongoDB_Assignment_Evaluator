package cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.parser.fsm.state;

import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.parser.fsm.ParserStateMachine;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.parser.fsm.state.comment.MultiLineCommentState;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.parser.fsm.state.comment.SingleLineCommentState;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.parser.fsm.state.query.DotState;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.parser.fsm.state.query.QueryBodyState;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.parser.fsm.state.query.StringState;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.parser.iterator.LineIterator;
import cz.cvut.fel.mongodb_assignment_evaluator.enums.ParserStates;

public class ScriptState extends ParserState {
    public ScriptState(ParserStateMachine context, ParserStates previousState) {
        super(context, previousState);
    }

//    @Override
//    public void process(LineIterator iterator) {
//        if (!iterator.contains("//")
//                && !iterator.contains("/*")
//                && !iterator.contains("db.")) {
//            iterator.nextAll();
//        } else if (iterator.startsWith("//")) {
//            context.setCurrentState(new SingleLineCommentState(context));
//        } else if (iterator.startsWith("/*")) {
//            context.setCurrentState(new MultiLineCommentState(context));
//        } else if (iterator.startsWith("db.")) {
//            context.resetAccumulators();
//            context.setCurrentPosition(iterator);
//            context.appendToQuery(iterator.consumeMatch("db"));
//            context.setCurrentState(new QueryState(context, false));
//        } else if (iterator.startsWithStringConstruct()) {
//            iterator.nextStringConstruct();
//        } else {
//            iterator.next();
//        }
//    }

    @Override
    public void process(LineIterator iterator) {
        if (!canTransition(iterator)) {
            iterator.nextAll();
        } else if (iterator.startsWith("//")) {
            context.transition(new SingleLineCommentState(context, this));
        } else if (iterator.startsWith("/*")) {
            context.transition(new MultiLineCommentState(context, this));
        } else if (iterator.startsWithStringQuote()) {
            context.transition(new StringState(context, this, iterator.next()));
        } else if (iterator.startsWith("db")) {
//            context.resetAccumulators();
//            context.setCurrentPosition(iterator);
            context.appendToQuery(iterator.nextMatch("db"));
            context.transition(new DotState(context, this));
//            context.transition(new QueryBodyState(context, getEnumeration(), false));
        } else {
            // wait for a transition
            iterator.next();
        }
    }

//    @Override
//    public ParserStates getEnumeration() {
//        return ParserStates.SCRIPT;
//    }

    private boolean canTransition(LineIterator iterator) {
        return iterator.contains("//") ||
                iterator.contains("/*") ||
                iterator.contains("db") ||
                iterator.contains(".");
    }
}
