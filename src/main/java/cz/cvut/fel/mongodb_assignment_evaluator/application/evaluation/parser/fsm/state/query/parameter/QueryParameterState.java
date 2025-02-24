package cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.parser.fsm.state.query.parameter;

import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.parser.fsm.ParserStateMachine;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.parser.fsm.state.ParserState;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.parser.fsm.state.comment.MultiLineCommentState;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.parser.fsm.state.comment.SingleLineCommentState;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.parser.fsm.state.query.QueryBodyState;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.parser.fsm.state.query.QueryEndState;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.parser.fsm.state.query.StringState;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.parser.iterator.LineIterator;
import cz.cvut.fel.mongodb_assignment_evaluator.application.model.parameter.EmptyParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.enums.ParserStates;
import lombok.Getter;

public class QueryParameterState extends ParserState {
    private final Boolean isModifier;

    public QueryParameterState(ParserStateMachine context, ParserState previousState, Boolean isModifier) {
        super(context, previousState);
        this.isModifier = isModifier;
    }

    @Override
    public void process(LineIterator iterator) {
//        if (iterator.startsWith("(") || iterator.startsWith(",")) {
//            context.appendToQuery(iterator.next());
//            iterator.skipWhitespaces();
//            if (iterator.startsWith(")") || iterator.startsWith(",")) {
//                context.addParameter(new EmptyParameter(), isModifier);
//            }
//        }
//        iterator.skipWhitespaces();

        if (iterator.startsWith("//")) {
            context.transition(new SingleLineCommentState(context, this));
        } else if (iterator.startsWith("/*")) {
            context.transition(new MultiLineCommentState(context, this));
        } else if (iterator.startsWith("{")) {
            context.transition(new DocumentParameterState(context, this, isModifier, false));
        } else if (iterator.startsWith("[")) {
            iterator.next();
            context.transition(new DocumentParameterState(context, this, isModifier, true));
        } else if (iterator.startsWithStringQuote()) {
            context.transition(new StringParameterState(context, this, isModifier));
        } else if (iterator.startsWith(")")) {
            iterator.next();
            // todo if builder.isEmpty() save empty param
            context.transition(new QueryEndState(context, this));
        } else if (iterator.startsWith(",")) {
            iterator.next();
            // todo if builder.isEmpty() save empty param
        } else if (iterator.startsWithWhitespace()) {
            iterator.next();
        } else if (iterator.hasNext()) {
            context.transition(new FunctionParameterState(context, this));
//            context.setState(new StringLiteralState(context, isModifier));
        }
    }
}