package cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.parser.fsm.state.script.query.parameter.document;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.parser.ScriptParser;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.parser.fsm.state.ParserState;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.parser.iterator.LineIterator;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.parser.preprocessor.HexIdGenerator;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.parser.preprocessor.StringPreprocessor;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.utility.StringUtility;

import java.time.LocalDate;

/**
 * A state that keeps track of the "(" parenthesis and accumulates values until it closes the last one.
 * After encountering the last ")" it preprocesses the accumulated values for the JSON parser and returns back to the previous state.
 */
public class ObjectValueState extends ParserState {
    private final String lastBufferedWord;
    private int parenthesisDepth;
    private final StringBuilder buffer;

    public ObjectValueState(ScriptParser context, ParserState previousState, String lastBufferedWord) {
        super(context, previousState);
        this.lastBufferedWord = lastBufferedWord;
        parenthesisDepth = 1;
        buffer = new StringBuilder();
    }

    @Override
    public void process(LineIterator iterator) {
        if (iterator.startsWith("(")) {
            parenthesisDepth++;
        } else if (iterator.startsWith(")")) {
            parenthesisDepth--;
        }
        String next = (iterator.startsWithStringQuote()) ? iterator.nextStringConstruct() : String.valueOf(iterator.next());
        if (parenthesisDepth > 0) {
            if (next.isBlank()) {
                if (!Character.isWhitespace(StringUtility.getLastChar(buffer.toString()))) {
                    buffer.append(" ");
                }
            } else {
                buffer.append(next);
            }
        } else if (parenthesisDepth == 0) {
            String bufferedString = buffer.toString();
            context.appendToRawQuery(bufferedString + ")");
            bufferedString = StringPreprocessor.preprocess(bufferedString);
            if (!previousState.getClass().equals(NestedQueryState.class)) {
                if (lastBufferedWord.endsWith("ISODate(")) {
                    bufferedString = LocalDate.now().toString();
                } else if (lastBufferedWord.endsWith("ObjectId(")) {
                    bufferedString = HexIdGenerator.generateHexId(24);
                }
                bufferedString = "\"" + bufferedString + "\"";
            }
            context.accumulate(bufferedString);
            if (!lastBufferedWord.equalsIgnoreCase("ObjectId(")) {
                context.accumulate(")");
            }
            context.transition(previousState);
        }
    }
}
