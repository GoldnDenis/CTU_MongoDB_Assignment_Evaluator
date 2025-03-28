package cz.cvut.fel.mongodb_assignment_evaluator.infrastructure.utility;

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
}
