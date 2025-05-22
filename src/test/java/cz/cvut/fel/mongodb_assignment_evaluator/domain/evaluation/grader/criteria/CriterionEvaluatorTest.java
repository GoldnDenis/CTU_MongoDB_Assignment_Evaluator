package cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.grader.criteria;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.enums.AggregationOperators;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.enums.Criteria;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.enums.QuerySelectors;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.grader.criteria.aggregate.AggregationCriterionEvaluator;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.grader.criteria.factory.CriteriaFactory;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.grader.criteria.find.FindSelectorCriterionEvaluator;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.grader.criteria.find.ProjectionCriterionEvaluator;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.grader.criteria.general.CommandCriterionEvaluator;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.grader.criteria.update.UpsertCriterionEvaluator;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.parser.ScriptParser;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.StudentSubmission;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.entity.Criterion;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.entity.Submission;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type.AggregateQuery;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type.FindQuery;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type.MongoQuery;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type.UpdateQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CriterionEvaluatorTest {
    private ScriptParser scriptParser;
    private StudentSubmission submission;

    @BeforeEach
    void setUp() {
        scriptParser = new ScriptParser();
        Submission mockEntity = Mockito.mock(Submission.class);
        submission = new StudentSubmission(mockEntity, false, 1);
    }

    @Test
    void test_evaluate_AggregationCriterionEvaluator_ContainsMatch() {
        Criterion criterion = createCriterion(Criteria.STAGE_MATCH);
        String script = "db.criteria.aggregate([{ $match: { status: 'Fulfilled' } }]);";
        int expectedScore = 1;

        // parse script
        List<MongoQuery> extractedQueries = scriptParser.parse(submission, script);
        assertEquals(1, extractedQueries.size());
        MongoQuery query = extractedQueries.get(0);
        assertInstanceOf(AggregateQuery.class, query);
        AggregateQuery aggregateQuery = (AggregateQuery) query;

        // evaluate queries
        CriterionEvaluator<AggregateQuery> evaluator = new AggregationCriterionEvaluator(criterion, AggregationOperators.MATCH);
        evaluator.evaluate(aggregateQuery);

        assertEquals(expectedScore, evaluator.currentScore);
    }

    @Test
    void test_evaluate_FindSelectorCriterionEvaluator_ContainsLogical() {
        Criterion criterion = createCriterion(Criteria.FIND_LOGICAL_OPERATOR);

        String script = "db.criteria.find({ $not: { $eq: 'Test' } });";
        int expectedScore = 1;

        // parse script
        List<MongoQuery> extractedQueries = scriptParser.parse(submission, script);
        assertEquals(1, extractedQueries.size());
        MongoQuery query = extractedQueries.get(0);
        assertInstanceOf(FindQuery.class, query);
        FindQuery findQuery = (FindQuery) query;

        // evaluate queries
        CriterionEvaluator<FindQuery> evaluator = new FindSelectorCriterionEvaluator(criterion, QuerySelectors.LOGICAL);
        evaluator.evaluate(findQuery);

        assertEquals(expectedScore, evaluator.currentScore);
    }

    @Test
    void test_evaluate_ProjectionCriterionEvaluator_ContainsNegative() {
        Criterion criterion = createCriterion(Criteria.NEGATIVE_PROJECTION);
        String script = "db.criteria.find({ name: 'any' }, { _id: 0, name: 0, surname: 0 });";
        int expectedScore = 1;

        // parse script
        List<MongoQuery> extractedQueries = scriptParser.parse(submission, script);
        assertEquals(1, extractedQueries.size());
        MongoQuery query = extractedQueries.get(0);
        assertInstanceOf(FindQuery.class, query);
        FindQuery findQuery = (FindQuery) query;

        // evaluate queries
        CriterionEvaluator<FindQuery> evaluator = new ProjectionCriterionEvaluator(criterion, false);
        evaluator.evaluate(findQuery);

        assertEquals(expectedScore, evaluator.currentScore);
    }

    @Test
    void test_evaluate_UpsertCriterionEvaluator() {
        Criterion criterion = createCriterion(Criteria.UPSERT_USED);
        String script = "db.tests.updateOne({ id: 2 }, {}, { upsert: true });";
        int expectedScore = 1;

        // parse script
        List<MongoQuery> extractedQueries = scriptParser.parse(submission, script);
        assertEquals(1, extractedQueries.size());
        MongoQuery query = extractedQueries.get(0);
        assertInstanceOf(UpdateQuery.class, query);
        UpdateQuery updateQuery = (UpdateQuery) query;

        // evaluate queries
        CriterionEvaluator<UpdateQuery> evaluator = new UpsertCriterionEvaluator(criterion);
        evaluator.evaluate(updateQuery);

        assertEquals(expectedScore, evaluator.currentScore);
    }

    private Criterion createCriterion(Criteria criterion) {
        return new Criterion(
                criterion.name(),
                criterion.getDescription(),
                criterion.ordinal(),
                criterion.getRequiredCount()
        );
    }
}