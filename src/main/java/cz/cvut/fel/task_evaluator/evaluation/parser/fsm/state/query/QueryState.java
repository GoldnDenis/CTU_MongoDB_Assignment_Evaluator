package cz.cvut.fel.task_evaluator.evaluation.parser.fsm.state.query;


import cz.cvut.fel.task_evaluator.evaluation.parser.fsm.ParserStateMachine;
import cz.cvut.fel.task_evaluator.evaluation.parser.fsm.state.ParserState;
import cz.cvut.fel.task_evaluator.evaluation.parser.fsm.state.ParserTransitionState;
import cz.cvut.fel.task_evaluator.evaluation.parser.fsm.state.query.parameter.ParameterState;
import cz.cvut.fel.task_evaluator.evaluation.parser.iterator.LineIterator;

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
                isModifier = false;
            }
            iterator.next();
            if (iterator.startsWith(";")) {
                iterator.next();
                context.saveQuery();
                context.setState(new ParserTransitionState(context));
            } else if (iterator.startsWith(".")) {
                isModifier = true;
            } else {
                // todo syntax error
                processSyntaxError("Invalid query syntax", iterator);
            }
        } else {
            // todo syntax error
            processSyntaxError("Invalid query syntax", iterator);
        }
    }
}