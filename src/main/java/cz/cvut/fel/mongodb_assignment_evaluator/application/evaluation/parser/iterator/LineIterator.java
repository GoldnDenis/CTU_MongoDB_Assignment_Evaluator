package cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.parser.iterator;

import lombok.Getter;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.regex.Pattern;

@Getter
public class LineIterator implements Iterator<Character> {
    private final String line;
//    private int rowIndex;
    private int currentIndex;

//    private final Pattern stringConstructPattern;

    public LineIterator(String line) {
        this.line = line;
        this.currentIndex = 0;
    }

//    public LineIterator() {
//        this.stringConstructPattern = Pattern.compile("^((\"(\\\\\\\"|[^\"\\n])*\")|('(\\\\\\'|[^'\\n])*)')");
//        setLine("", 0);
//    }

//    public void setLine(String line, int rowIndex) {
//        this.line = line;
//        this.rowIndex = rowIndex;
//        this.currentIndex = 0;
//    }

    @Override
    public boolean hasNext() {
        return currentIndex < line.length();
    }

    @Override
    public Character next() {
        if (!hasNext()) {
            throw new NoSuchElementException("End of input");
        }
        return line.charAt(currentIndex++);
    }

    public String nextAll() {
        int start = currentIndex;
        currentIndex = line.length();
        return line.substring(start);
    }

    public String nextMatch(String substring) {
        int end = line.substring(currentIndex).indexOf(substring);
        if (end == -1) {
            throw new NoSuchElementException("No such substring");
        }
        int start = currentIndex;
        currentIndex += (end + substring.length());
        return line.substring(start, currentIndex);
    }

    public Character peek() {
        if (!hasNext()) {
            throw new NoSuchElementException("End of input");
        }
        return line.charAt(currentIndex);
    }

    public String peekLine() {
        return line.substring(currentIndex);
    }

    public boolean contains(String substring) {
        return line.substring(currentIndex).contains(substring);
    }

    public boolean startsWith(String substring) {
        return line.substring(currentIndex).startsWith(substring);
    }

    public boolean startsWith(char c) {
        return hasNext() && peek() == c;
    }

    public boolean startsWithWhitespace() {
        return hasNext() && Character.isWhitespace(peek());
    }

    public boolean startsWithStringQuote() {
        String substring = line.substring(currentIndex);
        return substring.startsWith("\"") || substring.startsWith("'");
    }

    public boolean endsWith(String substring) {
        return line.substring(currentIndex).endsWith(substring);
    }

//    public String nextStringConstruct() {
//        int start = currentIndex;
//        char quote = peek();
//        String escapeSequence = "\\" + quote;
//        next();
//        while (hasNext()) {
//            char c = next();
//            if (startsWith(escapeSequence)) {
//                consumeMatch(escapeSequence);
//            } else if (c == quote) {
//                break;
//            }
//        }
//        return line.substring(start, currentIndex);
//    }

//    public String nextUntilWhitespace() {
//        int start = currentIndex;
//        while (hasNext()) {
//            char c = peek();
//            if (Character.isWhitespace(c)) {
//                break;
//            }
//            next();
//        }
//        return line.substring(start, currentIndex);
//    }

//    public String consumeMatchOrAll(String substring) {
//        int end = indexOf(substring);
//        if (end == -1) {
//            return nextAll();
//        }
//        int position = this.currentIndex;
//        this.currentIndex += end + substring.length();
//        return line.substring(position, currentIndex);
//    }

//    public String consumeMatch(String substring) {
//        int end = indexOf(substring);
//        if (end == -1) {
//            return "";
//        }
//        int position = this.currentIndex;
//        this.currentIndex += end + substring.length();
//        return line.substring(position, currentIndex);
//    }

//    public void skipWhitespaces() {
//        while (hasNext()) {
//            char c = peek();
//            if (!Character.isWhitespace(c)) {
//                break;
//            }
//            next();
//        }
//    }

//    public boolean startsWithStringConstruct() {
//        return stringConstructPattern.matcher(line.substring(currentIndex)).find();
//    }

//    public int indexOf(String substring) {
//        return line.substring(this.currentIndex).indexOf(substring);
//    }
//
//    public int indexOf(char c) {
//        return line.substring(this.currentIndex).indexOf(c);
//    }
}
