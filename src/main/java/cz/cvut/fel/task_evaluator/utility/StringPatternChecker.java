package cz.cvut.fel.task_evaluator.utility;

import java.util.regex.Pattern;

public class StringPatternChecker {
    private final static Pattern stringConstructPattern = Pattern.compile("^((\"(\\\\\\\"|[^\"\\n])*\")|('(\\\\\\'|[^'\\n])*)')");

    public static boolean isStringConstruct(String str) {
        return stringConstructPattern.matcher(str).find();
    }
}
