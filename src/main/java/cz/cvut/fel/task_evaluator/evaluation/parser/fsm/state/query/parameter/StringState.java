package cz.cvut.fel.task_evaluator.evaluation.parser.fsm.state.query.parameter;


import cz.cvut.fel.task_evaluator.evaluation.parser.fsm.ParserStateMachine;
import cz.cvut.fel.task_evaluator.evaluation.parser.fsm.state.ParserState;
import cz.cvut.fel.task_evaluator.evaluation.parser.fsm.state.ParserTransitionState;
import cz.cvut.fel.task_evaluator.evaluation.parser.iterator.LineIterator;
import cz.cvut.fel.task_evaluator.model.query.parameter.StringParameter;

public class StringState extends ParserState {
    private boolean isModifier;

    public StringState(ParserStateMachine context, Boolean isModifier) {
        super(context);
        this.isModifier = isModifier;
    }

    @Override
    public void process(LineIterator iterator) {
        iterator.skipWhitespaces();
        if (iterator.startsWith(",") || iterator.startsWith(")")) {
            context.addParameter(new StringParameter(valueAccumulator.toString()), isModifier);
            context.setState(new ParameterState(context, isModifier));
        } else if (iterator.startsWith("+")) {
            iterator.next();
            iterator.skipWhitespaces();
        } else if (iterator.startsWithStringConstruct()) {
            String strConstruct = iterator.extractStringConstruct();
            if (!valueAccumulator.isEmpty()) {
                valueAccumulator.append(" ");
            }
            valueAccumulator.append(strConstruct.substring(1, strConstruct.length() - 1));
        } else if (iterator.hasNext()) {
            // todo syntax error
            context.setState(new ParserTransitionState(context));
        }
    }
}
