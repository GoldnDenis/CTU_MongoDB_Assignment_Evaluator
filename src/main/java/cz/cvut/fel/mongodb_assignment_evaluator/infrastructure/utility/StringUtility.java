package cz.cvut.fel.mongodb_assignment_evaluator.infrastructure.utility;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.parser.iterator.LineIterator;

public class StringUtility {
    public static char getLastChar(String string) {
        int length = string.length();
        if (length < 1) {
            return '\0';
        }
        int index = length - 1;
        return string.charAt(index);
    }

    public static boolean isWhitespace(String string) {
        for (char c : string.toCharArray()) {
            if (!Character.isWhitespace(c)) {
                return false;
            }
        }
        return true;
    }

    public static Double parseDouble(String string) {
        try {
            return Double.parseDouble(string);
        } catch (NumberFormatException e) {
            return Double.NaN;
        }
    }

    public static boolean isNumber(String string) {
        return !Double.isNaN(parseDouble(string));
    }

    public static String substringTillParenthesis(String string, String substring, char openingParenthesis) {
        if (string == null) {
            return string;
        }
        char closingParenthesis = openingParenthesis;
        switch (openingParenthesis) {
            case '[' -> closingParenthesis = ']';
            case '(' -> closingParenthesis = ')';
            case '{' -> closingParenthesis = '}';
            default -> {
                return string;
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(substring);
        int parenthesisCount = 1;
        LineIterator iterator = new LineIterator(string.substring(string.indexOf(substring) + substring.length()));
        while (parenthesisCount > 0 && iterator.hasNext()) {
            char next = iterator.next();
            stringBuilder.append(next);
            if (next == openingParenthesis) {
                parenthesisCount++;
            } else if (next == closingParenthesis) {
                parenthesisCount--;
            }
        }
        if (parenthesisCount > 0) {
            return string;
        }
        return stringBuilder.toString();
    }
}
