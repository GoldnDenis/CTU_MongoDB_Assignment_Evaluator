package cz.cvut.fel.task_evaluator.evaluation.parser.tokenization.state.query.parameter;

import cz.cvut.fel.task_evaluator.enums.TokenTypes;
import cz.cvut.fel.task_evaluator.evaluation.parser.iterator.LineIterator;
import cz.cvut.fel.task_evaluator.evaluation.parser.tokenization.ParserStateMachine;
import cz.cvut.fel.task_evaluator.evaluation.parser.tokenization.state.ParserState;
import cz.cvut.fel.task_evaluator.evaluation.parser.tokenization.state.query.QueryState;
import cz.cvut.fel.task_evaluator.evaluation.parser.tokenization.state.transition.ParserTransitionState;
import cz.cvut.fel.task_evaluator.model.query.parameter.DocumentParameter;
import cz.cvut.fel.task_evaluator.model.query.parameter.EmptyParameter;
import cz.cvut.fel.task_evaluator.model.query.parameter.QueryParameter;
import cz.cvut.fel.task_evaluator.model.query.parameter.StringParameter;

public class ParameterState extends ParserState {
    private Boolean isModifier;

    public ParameterState(ParserStateMachine context, Boolean isModifier) {
        super(context);
        this.isModifier = isModifier;
    }

    @Override
    public void process(LineIterator iterator) {
        if (iterator.startsWith("(") || iterator.startsWith(",")) {
            iterator.next();
            iterator.skipWhitespaces();
            if (iterator.startsWith(")") || iterator.startsWith(",")) {
                context.addParameter(new EmptyParameter(), isModifier);
            }
        }
        iterator.skipWhitespaces();

        if (iterator.startsWithStringConstruct()) {
            context.setState(new StringState(context, isModifier));
        } else if (iterator.startsWith("{")) {
            context.setState(new DocumentState(context, isModifier, false));
        } else if (iterator.startsWith("[")) {
            iterator.next();
            context.setState(new DocumentState(context, isModifier, true));
        } else if (iterator.startsWith(")")) {
            context.setState(new QueryState(context, isModifier));
        } else if (iterator.hasNext()) {
            // todo syntax error
            context.setState(new ParserTransitionState(context));
        }
    }
}
