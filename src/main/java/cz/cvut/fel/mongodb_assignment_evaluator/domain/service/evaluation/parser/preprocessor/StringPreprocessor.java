package cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.parser.preprocessor;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.enums.RegularExpressions;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.parser.ExpressionEvaluator;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.parser.iterator.LineIterator;
import cz.cvut.fel.mongodb_assignment_evaluator.infrastructure.utility.StringUtility;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringPreprocessor {
    public static String preprocessEJson(String eJson) {
        eJson = eJson.replaceAll("\\/\\*.*?\\*\\/", "");
        eJson = eJson.replaceAll("ObjectId\\([a-zA-Z0-9]*\\)", generateHex24());
        eJson = processDate(eJson);
        eJson = processNestedQuery(eJson);
        eJson = processVariableCall(eJson);
        return eJson;
    }

    private static String processNestedQuery(String eJson) {
        Matcher matcher = Pattern.compile(RegularExpressions.MONGODB_QUERY_START.getRegex()).matcher(eJson);
        while (matcher.find()) {
            String query = StringUtility.substringTillParenthesis(eJson, matcher.group(), '(');
            String rest = eJson.substring(eJson.indexOf(query) + query.length());
            matcher = Pattern.compile(RegularExpressions.NESTED_MODIFIER.getRegex()).matcher(rest);
            if (matcher.find()) {
                query = query + matcher.group();
            }
            String replacement = '"' + query.trim().replaceAll("\"", "\\\\\"") + '"';
            eJson = eJson.replace(query, replacement);
        }
        return eJson;
    }

    private static String processVariableCall(String eJson) {
        String rest = eJson;
        Matcher matcher = Pattern.compile(RegularExpressions.VARIABLE_CALL.getRegex()).matcher(rest);
        while (matcher.find()) {
            String variableCall = StringUtility.substringTillParenthesis(rest, matcher.group(), '[');
            String replacement = '"' + variableCall.trim().replaceAll("\"", "\\\\\"") + '"';
            eJson = eJson.replace(variableCall, replacement);
            rest = eJson.substring(eJson.indexOf(variableCall) + variableCall.length());
        }
        return eJson;
    }

    private static String processDate(String eJson) {
        Matcher matcher = Pattern.compile(RegularExpressions.DATE_FIELD.getRegex()).matcher(eJson);
        while (matcher.find()) {
            String dateString = matcher.group(4);
            String innerDateString = matcher.group(7);
            String[] parts = innerDateString != null ? innerDateString.split("-") : new String[]{};
            String replacement = dateString;
            if (dateString == null ||
                    (!dateString.trim().startsWith("\"") && !dateString.trim().startsWith("'"))) {
                replacement = '"' + LocalDate.now().toString() + '"';
            } else if (parts.length > 0) {
                StringBuilder newInnerDateString = new StringBuilder();
                for (int i = 0; i < parts.length; i++) {
                    String part = parts[i];
                    if (part.length() == 1) {
                        newInnerDateString.append("0");
                    }
                    newInnerDateString.append(part);
                    if (i != parts.length - 1) {
                        newInnerDateString.append("-");
                    }
                }
                replacement = '"' + newInnerDateString.toString() + '"';
            }
            replacement = "ISODate(" + replacement + ")";
            eJson = eJson.replace(matcher.group(), replacement);
        }
        return eJson;
    }

    private static String generateHex24() {
        final String HEX_CHARS = "0123456789abcdef";
        SecureRandom random = new SecureRandom();
        StringBuilder hexString = new StringBuilder(24);

        hexString.append("\"");
        for (int i = 0; i < 24; i++) {
            int index = random.nextInt(HEX_CHARS.length());
            hexString.append(HEX_CHARS.charAt(index));
        }
        hexString.append("\"");

        return hexString.toString();
    }


    public static String preprocess(String string) {
        Pattern arithmeticExpressionPattern = Pattern.compile(RegularExpressions.ARITHMETIC_EXPRESSION.getRegex());
//        Pattern idFunctionPattern = Pattern.compile(RegularExpressions.ID_FUNCTION.getRegex());
        StringBuilder buffer = new StringBuilder();
        LineIterator iterator = new LineIterator(string);
        while (iterator.hasNext()) {
            if (iterator.startsWithStringQuote()) {
                buffer.append(iterator.nextStringConstruct());
            } else if (iterator.startsWith(arithmeticExpressionPattern)) {
                buffer.append(ExpressionEvaluator.eval(iterator.nextMatch(arithmeticExpressionPattern)).toString());
            }
//            else if (iterator.startsWith(idFunctionPattern)) {
//                iterator.nextMatch(idFunctionPattern);
//                buffer.append(generateHex24());
//            }
            else {
                char next = iterator.next();
                if (next == '"') {
                    buffer.append('\\');
                }
                buffer.append(next);
            }
        }
        return buffer.toString();
    }
}
