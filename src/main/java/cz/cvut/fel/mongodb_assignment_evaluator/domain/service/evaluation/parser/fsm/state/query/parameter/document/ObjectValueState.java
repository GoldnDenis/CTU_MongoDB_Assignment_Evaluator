package cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.parser.fsm.state.query.parameter.document;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.parser.ScriptParser;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.parser.fsm.state.ParserState;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.parser.iterator.LineIterator;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.parser.preprocessor.StringPreprocessor;
import cz.cvut.fel.mongodb_assignment_evaluator.infrastructure.utility.HexIdGenerator;
import cz.cvut.fel.mongodb_assignment_evaluator.infrastructure.utility.StringUtility;

import java.time.LocalDate;

public class ObjectValueState extends ParserState {
    private int parenthesisDepth;
    private final StringBuilder buffer;

    public ObjectValueState(ScriptParser context, ParserState previousState) {
        super(context, previousState);
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
            //todo possible to just utilize js evaluator fully
            String bufferedString = buffer.toString();
            bufferedString = StringPreprocessor.preprocess(bufferedString);
            if (!previousState.getClass().equals(NestedQueryState.class)) {
                if (context.getAccumulatedWord().endsWith("ISODate(")) {
                    bufferedString = LocalDate.now().toString();
                } else if (context.getAccumulatedWord().endsWith("ObjectId(")) {
                    bufferedString = HexIdGenerator.generateHexId(24);
                }
//                else if (!bufferedString.isBlank() && (!bufferedString.startsWith("\"") && !bufferedString.startsWith("'"))) {
//                    bufferedString = StringPreprocessor.preprocess(bufferedString);
//                }
                bufferedString = "\"" + bufferedString + "\"";
            }
            context.accumulate(bufferedString + ")");
            context.transition(previousState);
        }
    }

    private boolean isDoubleWhitespace(char current) {
        return Character.isWhitespace(current) &&
                Character.isWhitespace(StringUtility.getLastChar(context.getAccumulatedWord()));
    }
}
