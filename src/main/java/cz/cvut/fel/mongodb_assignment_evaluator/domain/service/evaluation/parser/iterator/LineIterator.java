package cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.parser.iterator;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.exceptions.IncorrectParserSyntax;
import lombok.Getter;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
public class LineIterator implements Iterator<Character> {
    private final String line;
    private int currentIndex;

    public LineIterator(String line) {
        this.line = line;
        this.currentIndex = 0;
    }

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

    public String nextMatch(Pattern pattern) {
        String rest = line.substring(currentIndex);
        Matcher matcher = pattern.matcher(rest);
        if (!matcher.find()) {
            throw new NoSuchElementException("No substring that matches the pattern");
        }
        int start = currentIndex;
        while (hasNext()) {
            if (matcher.lookingAt()) {
                currentIndex += matcher.group().length();
                break;
            }
            currentIndex++;
        }
        return line.substring(start, currentIndex);
    }

    // todo make a test
    public String nextStringConstruct() {
        if (!startsWithStringQuote() || !hasNext()) {
            throw new NoSuchElementException("Iterator did not find a string construct");
        }
        StringBuilder builder = new StringBuilder();
        char quote = next();
        String escape = "\\" + quote;
        builder.append(quote);
        while (hasNext()) {
            if (startsWith(escape)) {
                builder.append(nextMatch(escape));
            } else if (startsWith(quote)) {
                builder.append(next());
                return builder.toString();
            } else {
                builder.append(next());
            }
        }
        throw new IncorrectParserSyntax("Was expecting a closing string quote ('" + quote + "')");
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

    public boolean startsWith(Pattern pattern) {
        String rest = line.substring(currentIndex);
        Matcher matcher = pattern.matcher(rest);
        return matcher.lookingAt();
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
}
