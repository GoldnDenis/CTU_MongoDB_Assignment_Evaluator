package cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.parser.preprocessor;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.enums.RegularExpressions;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.parser.ExpressionEvaluator;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.parser.iterator.LineIterator;

import java.util.regex.Pattern;

public class StringPreprocessor {
    public static String preprocess(String string) {
        Pattern arithmeticExpressionPattern = Pattern.compile(RegularExpressions.ARITHMETIC_EXPRESSION.getRegex());
        StringBuilder buffer = new StringBuilder();
        LineIterator iterator = new LineIterator(string);
        while (iterator.hasNext()) {
            if (iterator.startsWithStringQuote()) {
                buffer.append(iterator.nextStringConstruct().replaceAll("\"", "\\\\\""));
            } else if (iterator.startsWith(arithmeticExpressionPattern)) {
                buffer.append(ExpressionEvaluator.eval(iterator.nextMatch(arithmeticExpressionPattern)));
            } else {
                buffer.append(iterator.next());
            }
        }
        return buffer.toString();
    }
}
