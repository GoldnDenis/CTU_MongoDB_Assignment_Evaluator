package cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.parser.fsm.state.query.parameter.document;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.enums.RegularExpressions;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.parser.ScriptParser;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.parser.fsm.state.ParserState;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.parser.iterator.LineIterator;

import java.util.regex.Pattern;

public class NestedQueryState extends ParserState {
    private static final Pattern nestedModifierPattern = Pattern.compile(RegularExpressions.NESTED_MODIFIER.getRegex());
    ;
    private boolean waitingModifier;

    public NestedQueryState(ScriptParser context, ParserState previousState) {
        super(context, previousState);
        waitingModifier = false;
    }

    @Override
    public void process(LineIterator iterator) {
        if (waitingModifier && iterator.startsWith(nestedModifierPattern)) { //todo
            context.accumulate(iterator.nextMatch(nestedModifierPattern));
        } else if (iterator.startsWith("(")) {
            context.accumulate(iterator.next());
            context.transition(new ObjectValueState(context, this));
        } else if (iterator.startsWith(".")) {
            waitingModifier = true;
        } else if (iterator.startsWith(",") || iterator.startsWith("}") || iterator.startsWith("]")) {
            context.accumulate("\"");
            context.transition(previousState);
        } else if (iterator.startsWithWhitespace()) {
            iterator.next();
        }
    }
}
