package cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.parser.fsm.state.query.parameter.document;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.enums.RegularExpressions;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.parser.ScriptParser;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.parser.fsm.state.ParserState;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.parser.fsm.state.comment.MultiLineCommentState;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.parser.fsm.state.comment.SingleLineCommentState;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.parser.iterator.LineIterator;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.parser.preprocessor.StringPreprocessor;
import cz.cvut.fel.mongodb_assignment_evaluator.infrastructure.utility.StringUtility;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DocumentValueState extends ParserState {
    private boolean openArray;
    private final StringBuilder buffer;

    public DocumentValueState(ScriptParser context, ParserState previousState) {
        super(context, previousState);
        openArray = false;
        buffer = new StringBuilder();
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
            context.accumulate(iterator.next());
            openArray = true;
        } else if (iterator.startsWith("]")) {
            acceptValue();
            context.accumulate(iterator.next());
            openArray = false;
        } else if (iterator.startsWith(",") && openArray) {
            acceptValue();
            context.accumulate(iterator.next());
        } else if (iterator.startsWith(",") || iterator.startsWith("}")) {
            acceptValue();
            context.transition(previousState);
        } else if (iterator.startsWithWhitespace()) {
            char c = iterator.next();
            if (!Character.isWhitespace(StringUtility.getLastChar(context.getAccumulatedWord()))) {
                buffer.append(c);
            }
        } else if (iterator.startsWith("(")) {
            String bufferedString = buffer.toString().trim();
            if (bufferedString.isBlank()) {
                throw new IllegalArgumentException("A value cannot start with '('"); // todo exception
            }
            if (bufferedString.equalsIgnoreCase("new Date")) {
                bufferedString = "ISODate";
            } else if (bufferedString.equalsIgnoreCase("UUID")) {
                bufferedString = "ObjectId";
            }
            bufferedString = bufferedString + iterator.next();
            if (Pattern.compile(RegularExpressions.MONGODB_QUERY_START.getRegex()).matcher(bufferedString).matches()) {
                bufferedString = "\"" + bufferedString;
            }
            buffer.setLength(0);
            context.accumulate(bufferedString);
            context.transition(new ObjectValueState(context, this));
        } else if (iterator.hasNext()) {
            char c = iterator.next();
            buffer.append(c);
        }
    }

    private void acceptValue() {
        String bufferedString = buffer.toString().trim();
        if (!bufferedString.isBlank()) {
            if (!StringUtility.isNumber(bufferedString) &&
                    (!bufferedString.startsWith("\"") && !bufferedString.startsWith("'"))) {
                bufferedString = "\"" + StringPreprocessor.preprocess(bufferedString) + "\"";
            }
            context.accumulate(bufferedString);
        }
        buffer.setLength(0);
    }

    private boolean isConstructor(String string) {
        return string.equalsIgnoreCase("ISODate") ||
                string.equalsIgnoreCase("ObjectId") ||
                string.equalsIgnoreCase("NumberLong") ||
                string.equalsIgnoreCase("NumberInt") ||
                string.equalsIgnoreCase("new ISODate");
    }
}
