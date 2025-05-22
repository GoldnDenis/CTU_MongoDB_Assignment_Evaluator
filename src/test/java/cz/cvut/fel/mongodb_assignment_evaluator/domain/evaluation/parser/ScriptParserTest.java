package cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.parser;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.StudentSubmission;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.entity.Submission;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type.CreateCollectionQuery;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type.FindQuery;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type.InsertQuery;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type.MongoQuery;
import org.bson.BsonDocument;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class ScriptParserTest {
    private ScriptParser scriptParser;
    private StudentSubmission submission;

    @BeforeEach
    void setUp() {
        scriptParser = new ScriptParser();
        Submission mockEntity = Mockito.mock(Submission.class);
        submission = new StudentSubmission(mockEntity, false, 1);
    }

    @Test
    void test_parse_SimpleScript_CorrectSyntax() {
        // sample data
        String script = "db.createCollection('dolls');" + System.lineSeparator()
                + "db.dolls.insertMany([" + "{name:'test1'}," + "{name: 'test2'}]);" + System.lineSeparator()
                + "db.dolls.find({name: 'test1'});" + System.lineSeparator();

        // expected results
        int expectedQuerySize = 3;

        String expectedCommandOne = "createCollection";
        Class<CreateCollectionQuery> expectedClassOne = CreateCollectionQuery.class;
        String expectedCollectionName = "dolls";

        String expectedCommandTwo = "insertMany";
        Class<InsertQuery> expectedClassTwo = InsertQuery.class;
        int expectedInsertedSize = 2;
        BsonDocument expectedInsertDocumentOne = BsonDocument.parse("{name:'test1'}");
        BsonDocument expectedInsertDocumentTwo = BsonDocument.parse("{name:'test2'}");

        String expectedCommandThree = "find";
        Class<FindQuery> expectedClassThree = FindQuery.class;
        BsonDocument expectedFilter = BsonDocument.parse("{name:'test1'}");

        // test
        List<MongoQuery> extractedQueries = scriptParser.parse(submission, script);

        /// validation
        assertEquals(expectedQuerySize, extractedQueries.size());
        // createCollection()
        MongoQuery currentQuery = extractedQueries.get(0);
        assertEquals(expectedCommandOne, currentQuery.getCommand());
        assertEquals(expectedClassOne, currentQuery.getClass());
        CreateCollectionQuery createCollectionQuery = expectedClassOne.cast(currentQuery);
        assertEquals(expectedCollectionName, createCollectionQuery.getCollectionName());
        // insertMany()
        currentQuery = extractedQueries.get(1);
        assertEquals(expectedCommandTwo, currentQuery.getCommand());
        assertEquals(expectedClassTwo, currentQuery.getClass());
        InsertQuery insertQuery = expectedClassTwo.cast(currentQuery);
        List<BsonDocument> insertedDocuments = insertQuery.getInsertedDocuments();
        assertEquals(expectedInsertedSize, insertedDocuments.size());
        assertEquals(expectedInsertDocumentOne, insertedDocuments.get(0));
        assertEquals(expectedInsertDocumentTwo, insertedDocuments.get(1));
        // find()
        currentQuery = extractedQueries.get(2);
        assertEquals(expectedCommandThree, currentQuery.getCommand());
        assertEquals(expectedClassThree, currentQuery.getClass());
        FindQuery findQuery = expectedClassThree.cast(currentQuery);
        assertEquals(expectedFilter, findQuery.getFilter());
    }

    @Test
    void test_parse_CreateCollection_CorrectSyntax() {
        // sample data
        String script = "db.createCollection('dolls');";

        // expected results
        int expectedQuerySize = 1;
        String expectedCommand = "createCollection";
        String expectedCollectionName = "dolls";
        Class<CreateCollectionQuery> expectedQueryClass = CreateCollectionQuery.class;

        // test
        List<MongoQuery> extractedQueries = scriptParser.parse(submission, script);

        // validation
        assertEquals(expectedQuerySize, extractedQueries.size());
        MongoQuery query = extractedQueries.getFirst();
        assertEquals(expectedQueryClass, query.getClass());
        CreateCollectionQuery createCollectionQuery = expectedQueryClass.cast(query);
        assertEquals(expectedCommand, createCollectionQuery.getCommand());
        assertEquals(expectedCollectionName, createCollectionQuery.getCollectionName());
    }

    @Test
    void test_parse_InsertOne_CorrectSyntax() {
        // sample data
        String script = "db.dolls.insertOne({ status: 'test' });";

        // expected results
        int expectedQuerySize = 1;
        String expectedCommand = "insertOne";
        String expectedCollectionName = "dolls";
        int expectedDocumentSize = 1;
        BsonDocument expectedDocument = BsonDocument.parse("{ status: 'test' }");
        Class<InsertQuery> expectedQueryClass = InsertQuery.class;

        // test
        List<MongoQuery> extractedQueries = scriptParser.parse(submission, script);

        // validation
        assertEquals(expectedQuerySize, extractedQueries.size());
        MongoQuery query = extractedQueries.getFirst();
        assertEquals(expectedQueryClass, query.getClass());
        assertEquals(expectedCommand, query.getCommand());
        assertEquals(expectedCollectionName, query.getCollection());
        InsertQuery insertQuery = expectedQueryClass.cast(query);
        List<BsonDocument> insertedDocuments = insertQuery.getInsertedDocuments();
        assertEquals(expectedDocumentSize, insertedDocuments.size());
        assertEquals(expectedDocument, insertedDocuments.getFirst());
    }

    @Test
    void test_parse_InsertOne_IncorrectCollectionName() {
        // sample data
        String script = "db.do ls.insertOne({ status: 'test' });";

        // expected results
        int expectedQuerySize = 0;
        int expectedLogSize = 1;
        String expectedMessage = "A collection/operator name cannot contain whitespace in-between";

        // test
        List<MongoQuery> extractedQueries = scriptParser.parse(submission, script);

        // validation
        assertEquals(expectedQuerySize, extractedQueries.size());
        List<String> errorLogs = submission.getErrorLogs();
        assertEquals(expectedLogSize, errorLogs.size());
        assertTrue(errorLogs.getFirst().contains(expectedMessage));
    }

    /**
     * Provides different test cases with missing semicolon
     * @return various script with described above problem
     */
    static Stream<String> scriptsMissingSemicolon() {
        return Stream.of(
                "db.createCollection('dolls')// error, does not have semicolon",
                "db.createCollection('dolls')",
                "db.createCollection('dolls')" + System.lineSeparator()
        );
    }

    @ParameterizedTest
    @MethodSource("scriptsMissingSemicolon")
    void test_parse_Query_MissingSemicolon(String script) {
        // expected results
        int expectedQuerySize = 0;
        int expectedLogSize = 1;
        String expectedMessage = "Expected a semicolon (;) or the start of a modifier (.) at the end of the query";

        // test
        List<MongoQuery> extractedQueries = scriptParser.parse(submission, script);

        // validation
        assertEquals(expectedQuerySize, extractedQueries.size());
        List<String> errorLogs = submission.getErrorLogs();
        assertEquals(expectedLogSize, errorLogs.size());
        assertTrue(errorLogs.getFirst().contains(expectedMessage));
    }

    /**
     * Provides different test cases where queries end abruptly
     * @return various script with described above problem
     */
    static Stream<String> scriptsAbruptlyEnded() {
        return Stream.of(
                "db.ins",
                "db.insertOne(",
                "db.insertOne({ id: "
        );
    }

    @ParameterizedTest
    @MethodSource("scriptsAbruptlyEnded")
    void test_parse_Query_AbruptlyEnded(String script) {
        // expected results
        int expectedQuerySize = 0;
        int expectedLogSize = 1;
        String expectedMessage = "Query is incomplete: the script ended before reaching a valid termination (e.g., semicolon or modifier)";

        // test
        List<MongoQuery> extractedQueries = scriptParser.parse(submission, script);

        // validation
        assertEquals(expectedQuerySize, extractedQueries.size());
        List<String> errorLogs = submission.getErrorLogs();
        assertEquals(expectedLogSize, errorLogs.size());
        assertTrue(errorLogs.getFirst().contains(expectedMessage));
    }

    @Test
    void test_parse_InsertOne_PreprocessedDocumentSameRawQuery() {
        // sample data
        String exptectedDocumentString = "{date:new Date(2 * new Date() + 1 - 2 * 2 )}";
        String script = "db.dolls.insertOne(" + exptectedDocumentString + ");";

        // expected results
        int expectedQuerySize = 1;
        int expectedDocumentSize = 1;
        Class<InsertQuery> expectedQueryClass = InsertQuery.class;

        // test
        List<MongoQuery> extractedQueries = scriptParser.parse(submission, script);

        // validation
        assertEquals(expectedQuerySize, extractedQueries.size());
        MongoQuery query = extractedQueries.getFirst();
        assertEquals(expectedQueryClass, query.getClass());
        InsertQuery insertQuery = expectedQueryClass.cast(query);
        List<BsonDocument> insertedDocuments = insertQuery.getInsertedDocuments();
        assertEquals(expectedDocumentSize, insertedDocuments.size());
        assertNotEquals(exptectedDocumentString, insertedDocuments.getFirst().toString());
        assertEquals(script, insertQuery.getQuery());
    }

    @Test
    void test_parse_InsertOne_IncorrectParameterUsage() {
        // sample data
        String script = "db.dolls.insertOne([{ status: 'test' }, { status: 'wrong' }]);";

        // expected results
        int expectedQuerySize = 0;
        int expectedLogSize = 1;
        String expectedMessage = "Array parameter is not allowed at 1 position for 'insertOne' query";

        Class<InsertQuery> expectedQueryClass = InsertQuery.class;

        // test
        List<MongoQuery> extractedQueries = scriptParser.parse(submission, script);

        // validation
        assertEquals(expectedQuerySize, extractedQueries.size());
        List<String> errorLogs = submission.getErrorLogs();
        assertEquals(expectedLogSize, errorLogs.size());
        assertTrue(errorLogs.getFirst().contains(expectedMessage));
    }
}