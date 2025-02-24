package cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.parser.fsm.state.query;

import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.parser.fsm.ParserStateMachine;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.parser.fsm.state.ParserState;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.parser.fsm.state.comment.MultiLineCommentState;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.parser.fsm.state.comment.SingleLineCommentState;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.parser.fsm.state.query.parameter.QueryParameterState;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.parser.iterator.LineIterator;
import cz.cvut.fel.mongodb_assignment_evaluator.enums.ParserStates;

public class QueryBodyState extends ParserState {
    private final boolean isModifier;

    public QueryBodyState(ParserStateMachine context, ParserState previousState, boolean isModifier) {
        super(context, previousState);
        this.isModifier = isModifier;
    }

    @Override
    public void process(LineIterator iterator) {
        if (iterator.startsWithWhitespace()) {
            iterator.next();
        } else if (iterator.startsWith("//")) {
            context.transition(new SingleLineCommentState(context, this));
        } else if (iterator.startsWith("/*")) {
            context.transition(new MultiLineCommentState(context, this));
        } else if (iterator.startsWith(".system")) {
            iterator.nextMatch(".system");
//             todo error processSyntaxError("'system.' prefix isn't allowed. (Reserved for internal use.)", iterator);
        } else if (iterator.startsWith(".")) {
            iterator.next();
            // todo context.setCurrentCollection(value);
            // reset builder
        } else if (iterator.startsWith("(")) {
            iterator.next();
            context.setQueryOperator(value);
            context.transition(new QueryParameterState(context, this, isModifier));
//        } else if (iterator.startsWith(")")) {
//            if (isModifier) {
//                context.saveModifier();
//            }
//            context.appendToQuery(iterator.next());
//            context.transition(new QueryEndState(context, getEnumeration(), isModifier));
        } else if (iterator.hasNext() ) {
            char c = iterator.next();
            if (!characterIsAllowed(c)) {
                // todo error processSyntaxError("'" + c + "' шs not allowed in collection, operation names", iterator);
                return;
            }
//            valueAccumulator.append(c);
        }
    }

    private boolean characterIsAllowed(char c) {
        return Character.isLetterOrDigit(c) || c == '_';
    }

    @Override
    public ParserStates getEnumeration() {
        return ParserStates.QUERY_BODY;
    }
}
