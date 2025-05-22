package cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.parser.iterator;

import org.junit.jupiter.api.Test;

import javax.sound.sampled.Line;

import java.util.NoSuchElementException;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

class LineIteratorTest {

    @Test
    void test_nextMatch_PatternMatches() {
        String line = "1 + 9 so easy";
        LineIterator lineIterator = new LineIterator(line);
        Pattern pattern = Pattern.compile("[0-9][ +]*[0-9]");

        // expected
        String expected = "1 + 9";

        // test
        String actual = lineIterator.nextMatch(pattern);
        assertEquals(expected, actual);
    }

    @Test
    void test_nextMatch_PatternNotMatches_thrownException() {
        String line = " one plus nine o easy";
        LineIterator lineIterator = new LineIterator(line);
        Pattern pattern = Pattern.compile("[0-9][ +]*[0-9]");

        // test
        assertThrows(NoSuchElementException.class, () -> {
            lineIterator.nextMatch(pattern);
        });
    }

    @Test
    void test_nextStringConstruct_StringConstructExtracted() {
        String line = "'this is so easy', she said";
        LineIterator lineIterator = new LineIterator(line);

        // expected
        String expected = "'this is so easy'";

        String actual = lineIterator.nextStringConstruct();
        assertEquals(expected, actual);
    }

    @Test
    void test_nextStringConstruct_NotContainClosingQuote_thrownException() {
        String line = "'this is so easy, she said";
        LineIterator lineIterator = new LineIterator(line);

        // test
        assertThrows(IllegalArgumentException.class, lineIterator::nextStringConstruct);
    }

    @Test
    void test_nextStringConstruct_NotContainStringConstruct_thrownException() {
        String line = "this is so easy, she said";
        LineIterator lineIterator = new LineIterator(line);

        // test
        assertThrows(NoSuchElementException.class, lineIterator::nextStringConstruct);
    }
}