package cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.grader.bson;

import org.bson.BsonDocument;
import org.bson.BsonString;
import org.bson.BsonType;
import org.bson.BsonValue;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

class BsonDocumentCheckerTest {

    @Test
    void test_findKeyMatchesPattern_EntireDocumentFound() {
        String documentString = "{ a: { d: 'vd', e: 've' }, b: 'vb', c: { f: { '1g': 'v' } } }";
        BsonDocument document = BsonDocument.parse(documentString);
        Pattern pattern = Pattern.compile("[0-9]g");

        // expected
        String expectedKey = "1g";

        // test
        Optional<String> optActualKey = BsonDocumentChecker.findKeyMatchesPattern(document, pattern);

        assertTrue(optActualKey.isPresent());
        String actualKey = optActualKey.get();
        assertEquals(expectedKey, actualKey);
    }

    @Test
    void test_findKeyMatchesPattern_DepthTwoNotFound() {
        String documentString = "{ a: { d: 'vd', e: 've' }, b: 'vb', c: { f: { '1g': 'v' } } }";
        BsonDocument document = BsonDocument.parse(documentString);
        Pattern pattern = Pattern.compile("[0-9]g");
        int depth = 2;

        // test
        Optional<String> optActualKey = BsonDocumentChecker.findKeyMatchesPattern(document, pattern, depth);

        assertTrue(optActualKey.isEmpty());
    }

    @Test
    void test_findValueMatchesPattern_EntireDocumentFound() {
        String documentString = "{ a: { d: 'vd', e: 've' }, b: 'vb', c: { f: { g: '1v' } } }";
        BsonDocument document = BsonDocument.parse(documentString);
        Pattern pattern = Pattern.compile("[0-9]v");

        // expected
        String expectedValue = "1v";

        // test
        Optional<String> optActualValue = BsonDocumentChecker.findValueMatchesPattern(document, pattern);

        assertTrue(optActualValue.isPresent());
        String actualValue = optActualValue.get();
        assertEquals(expectedValue, actualValue);
    }

    @Test
    void test_findValueMatchesPattern_DepthTwoNotFound() {
        String documentString = "{ a: { d: 'vd', e: 've' }, b: 'vb', c: { f: { g: '1v' } } }";
        BsonDocument document = BsonDocument.parse(documentString);
        Pattern pattern = Pattern.compile("[0-9]v");
        int depth = 2;

        // test
        Optional<String> optActualValue = BsonDocumentChecker.findValueMatchesPattern(document, pattern, depth);

        assertTrue(optActualValue.isEmpty());
    }

    @Test
    void test_containsValue_EntireDocumentFound() {
        String documentString = "{ a: { d: 'vd', e: 've' }, b: 'vb', c: { f: { g: 'vg' } } }";
        BsonDocument document = BsonDocument.parse(documentString);
        String key = "e";
        BsonValue value = new BsonString("ve");

        // test
        boolean result = BsonDocumentChecker.containsValue(document, value);

        assertTrue(result);
    }

    @Test
    void test_containsValue_DepthTwoNotFound() {
        String documentString = "{ a: { d: 'vd', e: 've' }, b: 'vb', c: { f: { g: 'vg' } } }";
        BsonDocument document = BsonDocument.parse(documentString);
        String key = "g";
        int depth = 2;
        BsonValue value = new BsonString("vg");

        // test
        boolean result = BsonDocumentChecker.containsValue(document, value, depth);

        assertFalse(result);
    }

    @Test
    void test_getRecursive_EntireDocumentFound() {
        String documentString = "{ a: { d: 'vd', e: 've' }, b: 'vb', c: { f: { g: 'vg' } } }";
        BsonDocument document = BsonDocument.parse(documentString);
        String key = "e";

        // expected
        String exptectedStringValue = "ve";

        // test
        Optional<BsonValue> actualValue = BsonDocumentChecker.getRecursive(document, key);

        assertTrue(actualValue.isPresent());
        BsonValue value = actualValue.get();
        assertTrue(value.isString());
        assertEquals(exptectedStringValue, value.asString().getValue());
    }

    @Test
    void test_getRecursive_DepthOneNotFound() {
        String documentString = "{ a: { d: 'vd', e: 've' }, b: 'vb', c: { f: { g: 'vg' } } }";
        BsonDocument document = BsonDocument.parse(documentString);
        String key = "e";
        int depth = 1;

        // test
        Optional<BsonValue> actualValue = BsonDocumentChecker.getRecursive(document, key, depth);

        assertTrue(actualValue.isEmpty());
    }

    @Test
    void test_getAll_DepthTwoFoundOne() {
        String documentString = "{ a: { d: 'vd', e: 've' }, b: 'vb', c: { f: { g: 'vg' } } }";
        BsonDocument document = BsonDocument.parse(documentString);
        Set<String> keySet = new HashSet<>(
                Set.of(
                        "b", "g"
                )
        );
        int depth = 2;

        // expected
        int expectedResultSize = 1;
        String expectedValue = "vb";

        // test
        List<BsonValue> foundValues = BsonDocumentChecker.getAll(document, keySet, depth);

        List<String> foundStringValues = foundValues.stream()
                .filter(BsonValue::isString)
                .map(BsonValue::asString)
                .map(BsonString::getValue)
                .toList();
        assertEquals(expectedResultSize, foundStringValues.size());
        assertEquals(expectedValue, foundStringValues.get(0));
    }

    @Test
    void test_getAll_EntireDocumentFoundTwo() {
        String documentString = "{ a: { d: 'vd', e: 've' }, b: 'vb', c: { f: { g: 'vg' } } }";
        BsonDocument document = BsonDocument.parse(documentString);
        Set<String> keySet = new HashSet<>(
                Set.of(
                    "b", "g"
                )
        );

        // expected
        int expectedResultSize = 2;
        String expectedValueOne = "vb";
        String expectedValueTwo = "vg";

        // test
        List<BsonValue> foundValues = BsonDocumentChecker.getAll(document, keySet);

        List<String> foundStringValues = foundValues.stream()
                .filter(BsonValue::isString)
                .map(BsonValue::asString)
                .map(BsonString::getValue)
                .toList();
        assertEquals(expectedResultSize, foundStringValues.size());
        assertEquals(expectedValueOne, foundStringValues.get(0));
        assertEquals(expectedValueTwo, foundStringValues.get(1));
    }

    @Test
    void test_containsValueOfType_ContainsDate() {
        String documentString = "{ a: { d: 'v' }, b: { f: 'v' }, c: ISODate() }";
        BsonDocument document = BsonDocument.parse(documentString);

        // test
        boolean result = BsonDocumentChecker.containsValueOfType(document, BsonType.DATE_TIME);

        assertTrue(result);
    }

    @Test
    void test_containsValueOfType_NotContainsDate() {
        String documentString = "{ a: { d: 'v' }, b: { f: 'v' }, c: 1 }";
        BsonDocument document = BsonDocument.parse(documentString);

        // test
        boolean result = BsonDocumentChecker.containsValueOfType(document, BsonType.DATE_TIME);

        assertFalse(result);
    }

    @Test
    void test_getDepth() {
        String documentString = "{ a: { d: 'v', e: 'v' }, b: 'v', c: { f: { g: 'v' } } }";
        BsonDocument document = BsonDocument.parse(documentString);

        // expected
        int expectedDepth = 3;

        // test
        int actualDepth = BsonDocumentChecker.getDepth(document);

        assertEquals(expectedDepth, actualDepth);
    }
}