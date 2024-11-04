package cz.cvut.fel.task_evaluator.evaluation.parser.tokenization.state.query;

import cz.cvut.fel.task_evaluator.enums.TokenTypes;
import cz.cvut.fel.task_evaluator.evaluation.parser.iterator.LineIterator;
import cz.cvut.fel.task_evaluator.evaluation.parser.tokenization.ParserStateMachine;
import cz.cvut.fel.task_evaluator.evaluation.parser.tokenization.state.ParserState;
import cz.cvut.fel.task_evaluator.evaluation.parser.tokenization.state.query.parameter.ParameterState;
import cz.cvut.fel.task_evaluator.evaluation.parser.tokenization.state.transition.ParserTransitionState;

public class QueryState extends ParserState {
    private boolean isModifier;

    public QueryState(ParserStateMachine context, Boolean isModifier) {
        super(context);
        this.isModifier = isModifier;
    }

    @Override
    public void process(LineIterator iterator) {
        if (iterator.startsWith(".")) {
            iterator.next();
            context.setState(new StringLiteralState(context, isModifier));
        } else if (iterator.startsWith("(")) {
            context.setState(new ParameterState(context, isModifier));
        } else if (iterator.startsWith(")")) {
            if (isModifier) {
                context.saveModifier();
            }
            iterator.next();
            if (iterator.startsWith(";")) {
                iterator.next();
                context.saveQuery();
                context.setState(new ParserTransitionState(context));
            } else if (iterator.startsWith(".")) {
                isModifier = true;
            }
        } else {
            // todo syntax error
            context.setState(new ParserTransitionState(context));
        }
    }
}