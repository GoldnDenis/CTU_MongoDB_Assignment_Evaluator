package cz.cvut.fel.mongodb_assignment_evaluator.evaluation.parser.iterator;

import lombok.Getter;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.regex.Pattern;

@Getter
public class LineIterator implements Iterator<Character> {
    private String text;
    private int rowIndex;
    private int currentColumnIndex;

    private final Pattern stringConstructPattern;

    public LineIterator() {
        this.stringConstructPattern = Pattern.compile("^((\"(\\\\\\\"|[^\"\\n])*\")|('(\\\\\\'|[^'\\n])*)')");
        changeText("", 0);
    }

    public void changeText(String text, int rowIndex) {
        this.text = text;
        this.rowIndex = rowIndex;
        this.currentColumnIndex = 0;
    }

    @Override
    public boolean hasNext() {
        return currentColumnIndex < text.length();
    }

    @Override
    public Character next() {
        if (!hasNext()) {
            throw new NoSuchElementException("End of input");
        }
        return text.charAt(currentColumnIndex++);
    }

    public String nextStringConstruct() {
        int start = currentColumnIndex;
        char quote = peek();
        String escapeSequence = "\\" + quote;
        next();
        while (hasNext()) {
            char c = next();
            if (startsWith(escapeSequence)) {
                consumeMatch(escapeSequence);
            } else if (c == quote) {
                break;
            }
        }
        return text.substring(start, currentColumnIndex);
    }

    public String nextUntilWhitespace() {
        int start = currentColumnIndex;
        while (hasNext()) {
            char c = peek();
            if (Character.isWhitespace(c)) {
                break;
            }
            next();
        }
        return text.substring(start, currentColumnIndex);
    }

    public String nextAll() {
        int position = this.currentColumnIndex;
        this.currentColumnIndex = text.length();
        return text.substring(position);
    }

    public String consumeMatchOrAll(String substring) {
        int end = indexOf(substring);
        if (end == -1) {
            return nextAll();
        }
        int position = this.currentColumnIndex;
        this.currentColumnIndex += end + substring.length();
        return text.substring(position, currentColumnIndex);
    }

    public String consumeMatch(String substring) {
        int end = indexOf(substring);
        if (end == -1) {
            return "";
        }
        int position = this.currentColumnIndex;
        this.currentColumnIndex += end + substring.length();
        return text.substring(position, currentColumnIndex);
    }

    public Character peek() {
        if (!hasNext()) {
            throw new NoSuchElementException("End of input");
        }
        return text.charAt(currentColumnIndex);
    }

    public void skipWhitespaces() {
        while (hasNext()) {
            char c = peek();
            if (!Character.isWhitespace(c)) {
                break;
            }
            next();
        }
    }

    public boolean startsWithStringConstruct() {
        return stringConstructPattern.matcher(text.substring(currentColumnIndex)).find();
    }

    public boolean contains(String substring) {
        return text.substring(this.currentColumnIndex).contains(substring);
    }

    public boolean startsWith(String substring) {
        return text.substring(this.currentColumnIndex).startsWith(substring);
    }

    public boolean endsWith(String substring) {
        return text.substring(this.currentColumnIndex).endsWith(substring);
    }

    public boolean equals(String string) {
        return text.substring(this.currentColumnIndex).equals(string);
    }

    public int indexOf(String substring) {
        return text.substring(this.currentColumnIndex).indexOf(substring);
    }

    public int indexOf(char c) {
        return text.substring(this.currentColumnIndex).indexOf(c);
    }
}
