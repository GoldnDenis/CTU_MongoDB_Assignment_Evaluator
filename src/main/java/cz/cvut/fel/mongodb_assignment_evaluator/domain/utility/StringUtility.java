package cz.cvut.fel.mongodb_assignment_evaluator.domain.utility;

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

    public static Integer parseInteger(String string) {
        try {
            return Integer.parseInt(string);
        } catch (NumberFormatException e) {
            return Integer.MIN_VALUE;
        }
    }

    public static boolean isDouble(String string) {
        return !Double.isNaN(parseDouble(string));
    }

    public static boolean isInt(String string) {
        return !parseInteger(string).equals(Integer.MIN_VALUE);
    }
}
