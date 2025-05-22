package cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.parser.fsm.state.script.query.parameter.document;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.enums.RegularExpressions;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.parser.ScriptParser;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.parser.fsm.state.ParserState;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.parser.fsm.state.script.MultiLineCommentState;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.parser.fsm.state.script.SingleLineCommentState;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.parser.iterator.LineIterator;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.parser.preprocessor.StringPreprocessor;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.exceptions.IncorrectParserSyntax;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.utility.StringUtility;

import java.util.regex.Pattern;

/**
 * Responsible for document value accumulation and its preprocessing for the parser.
 * Transitions to DocumentKeyState -by '{'.
 * Upon encountering the end of a document value ',' or '}', returns back to DocumentKeyState.
 * If it meets the pattern of a nested query it transitions to NestedQueryState.
 * If it meets the object initialization, i.e. "(", it transitions to ObjectValueState
 */
public class DocumentValueState extends ParserState {
    private final StringBuilder buffer;
    private int arrayCounter;

    public DocumentValueState(ScriptParser context, ParserState previousState) {
        super(context, previousState);
        buffer = new StringBuilder();
        arrayCounter = 0;
    }

    @Override
    public void process(LineIterator iterator) {
        if (iterator.startsWithStringQuote()) {
            buffer.append(iterator.nextStringConstruct());
        } else if (iterator.startsWith("//")) {
            context.transition(new SingleLineCommentState(context, this));
        } else if (iterator.startsWith("/*")) {
            context.transition(new MultiLineCommentState(context, this));
        } else if (iterator.startsWith("{")) {
            context.transition(new DocumentKeyState(context, this));
        } else if (iterator.startsWith("[")) {
            char next = iterator.next();
            if (arrayCounter == 0) {
                context.accumulate(next);
                context.appendToRawQuery(String.valueOf(next));
            } else {
                buffer.append(next);
            }
            arrayCounter++;
        } else if (iterator.startsWith("]")) {
            char next = iterator.next();
            arrayCounter--;
            if (arrayCounter == 0) {
                acceptValue();
                context.accumulate(next);
                context.appendToRawQuery(String.valueOf(next));
            } else {
                buffer.append(next);
            }
        } else if (iterator.startsWith(",") && arrayCounter > 0) {
            acceptValue();
            char next = iterator.next();
            context.accumulate(next);
            context.appendToRawQuery(String.valueOf(next));
        } else if (iterator.startsWith(",") || iterator.startsWith("}")) {
            acceptValue();
            context.transition(previousState);
        } else if (iterator.startsWithWhitespace()) {
            iterator.next();
            if (!Character.isWhitespace(StringUtility.getLastChar(buffer.toString()))) {
                buffer.append(" ");
            }
        } else if (iterator.startsWith("(")) {
            String bufferedString = buffer.toString().trim();
            if (bufferedString.isBlank()) {
                throw new IncorrectParserSyntax("A value cannot start with '('");
            }
            context.appendToRawQuery(bufferedString);
            if (bufferedString.equalsIgnoreCase("new Date")) {
                bufferedString = "ISODate";
            } else if (bufferedString.equalsIgnoreCase("UUID")) {
                bufferedString = "ObjectId";
            }
            if (Pattern.compile(RegularExpressions.MONGODB_QUERY_START.getRegex()).matcher(bufferedString).matches()) {
                bufferedString = "\"" + bufferedString;
                context.transition(new NestedQueryState(context, this));
            } else {
                char next = iterator.next();
                bufferedString = bufferedString + next;
                context.appendToRawQuery(String.valueOf(next));
                context.transition(new ObjectValueState(context, this, bufferedString));
            }
            buffer.setLength(0);
            if (!bufferedString.equalsIgnoreCase("ObjectId(")) {
                context.accumulate(bufferedString);
            }
        } else if (iterator.hasNext()) {
            buffer.append(iterator.next());
        }
    }

    private void acceptValue() {
        String bufferedString = buffer.toString().trim();
        if (!bufferedString.isBlank()) {
            context.appendToRawQuery(bufferedString);
            if (StringUtility.isInt(bufferedString)) {
                bufferedString = StringUtility.parseInteger(bufferedString).toString();
            } else if (StringUtility.isDouble(bufferedString)) {
                bufferedString = StringUtility.parseDouble(bufferedString).toString();
            } else if (!bufferedString.startsWith("\"") && !bufferedString.startsWith("'")) {
                bufferedString = "\"" + StringPreprocessor.preprocess(bufferedString) + "\"";
            }
            context.accumulate(bufferedString);
        }
        buffer.setLength(0);
    }
}
