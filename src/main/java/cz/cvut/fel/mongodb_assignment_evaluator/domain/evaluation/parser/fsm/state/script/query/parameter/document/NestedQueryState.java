package cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.parser.fsm.state.script.query.parameter.document;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.enums.RegularExpressions;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.parser.ScriptParser;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.parser.fsm.state.ParserState;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.parser.iterator.LineIterator;

import java.util.regex.Pattern;

/**
 * State that accumulates nested queries as string values.
 * After it is done, returns back to DocumentValueState.
 * After the "(" transitions to ObjectValueState.
 */
public class NestedQueryState extends ParserState {
    private static final Pattern nestedModifierPattern = Pattern.compile(RegularExpressions.NESTED_MODIFIER.getRegex());
    private boolean waitingModifier;

    public NestedQueryState(ScriptParser context, ParserState previousState) {
        super(context, previousState);
        waitingModifier = false;
    }

    @Override
    public void process(LineIterator iterator) {
        if (waitingModifier && iterator.startsWith(nestedModifierPattern)) {
            String next = iterator.nextMatch(nestedModifierPattern);
            context.accumulate(next);
            context.appendToRawQuery(next);
        } else if (iterator.startsWith("(")) {
            char next = iterator.next();
            context.accumulate(next);
            context.appendToRawQuery(String.valueOf(next));
            context.transition(new ObjectValueState(context, this, ""));
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
