package cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.grader.criteria.factory;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.enums.*;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.grader.criteria.CriterionEvaluator;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.grader.criteria.aggregate.AggregationCriterionEvaluator;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.grader.criteria.find.FindSelectorCriterionEvaluator;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.grader.criteria.find.FindTypeCriterionEvaluator;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.grader.criteria.find.ProjectionCriterionEvaluator;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.grader.criteria.general.*;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.grader.criteria.insert.InsertTypeCriterionEvaluator;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.grader.criteria.update.UpdateOperatorCriterionEvaluator;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.grader.criteria.update.UpdatePatternCriterionEvaluator;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.grader.criteria.update.UpsertCriterionEvaluator;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.exceptions.UnknownCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.entity.Criterion;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type.*;
import org.bson.BsonType;

/**
 * Matches criteria by name and initializes concrete Criteria Evaluators
 */
public class CriteriaFactory {
    /**
     * Initializes and returns a new concrete CriterionEvaluator class.
     * If factory does not contain criterion name throws UnknownCriterion exception
     * @param criterion
     * @return new concrete CriterionEvaluator class
     */
    public CriterionEvaluator<? extends MongoQuery> createEvaluationCriterion(Criterion criterion) {
        String name = criterion.getName().toUpperCase();
        try {
            Criteria foundCriterion = Criteria.valueOf(name);
            switch (foundCriterion) {
                // CreateCollection criteria
                case CREATE_ONE_COLLECTION -> {
                    return new CommandCriterionEvaluator<>(CreateCollectionQuery.class, criterion, MongoCommands.CREATE_COLLECTION);
                }
                // Insert criteria
                case INSERT_TEN_DOCUMENTS -> {
                    return new QuantityCriterionEvaluator<>(InsertQuery.class, criterion);
                }
                case INSERT_DOCUMENT_EMBEDDED -> {
                    return new InsertTypeCriterionEvaluator(criterion, BsonType.DOCUMENT);
                }
                case INSERT_DOCUMENT_ARRAY -> {
                    return new InsertTypeCriterionEvaluator(criterion, BsonType.ARRAY);
                }
                case INSERT_ONE_USED -> {
                    return new CommandCriterionEvaluator<>(InsertQuery.class, criterion, MongoCommands.INSERT_ONE);
                }
                case INSERT_MANY_USED -> {
                    return new CommandCriterionEvaluator<>(InsertQuery.class, criterion, MongoCommands.INSERT_MANY);
                }
                // Replace criteria
                case REPLACE_VALUE -> {
                    return new QuantityCriterionEvaluator<>(ReplaceQuery.class, criterion);
                }
                // Update criteria
                case UPDATE_ONE_DOCUMENT -> {
                    return new UpdateOperatorCriterionEvaluator(criterion, UpdateOperators.UPDATE, MongoCommands.UPDATE_ONE);
                }
                case UPDATE_MANY_FORMULA -> {
                    return new UpdateOperatorCriterionEvaluator(criterion, UpdateOperators.FORMULA, MongoCommands.UPDATE_MANY);
                }
                case UPDATE_ADD_FIELD -> {
                    return new UpdateOperatorCriterionEvaluator(criterion, UpdateOperators.ADD);
                }
                case UPDATE_REMOVE_FIELD -> {
                    return new UpdateOperatorCriterionEvaluator(criterion, UpdateOperators.REMOVE);
                }
                case UPDATE_NESTED_DOCUMENT -> {
                    return new UpdatePatternCriterionEvaluator(criterion, RegularExpressions.NESTED_FIELD, UpdateOperators.ADD);
                }
                case UPDATE_ARRAY -> {
                    return new UpdateOperatorCriterionEvaluator(criterion, UpdateOperators.ARRAY);
                }
                case UPSERT_USED -> {
                    return new UpsertCriterionEvaluator(criterion);
                }
                // Find criteria
                case FIND_FIVE -> {
                    return new QuantityCriterionEvaluator<>(FindQuery.class, criterion);
                }
                case FIND_LOGICAL_OPERATOR -> {
                    return new FindSelectorCriterionEvaluator(criterion, QuerySelectors.LOGICAL);
                }
                case FIND_COMPARISON -> {
                    return new FindSelectorCriterionEvaluator(criterion, QuerySelectors.COMPARISON);
                }
                case FIND_ARRAY_OPERATOR -> {
                    return new FindSelectorCriterionEvaluator(criterion, QuerySelectors.ARRAY);
                }
                case FIND_DATE_OPERATOR -> {
                    return new FindTypeCriterionEvaluator(criterion, BsonType.DATE_TIME);
                }
                case POSITIVE_PROJECTION -> {
                    return new ProjectionCriterionEvaluator(criterion, true);
                }
                case NEGATIVE_PROJECTION -> {
                    return new ProjectionCriterionEvaluator(criterion, false);
                }
                case SORT_MODIFIER -> {
                    return new ModifierCommandCriterionEvaluator<>(FindQuery.class, criterion, MongoCommands.SORT);
                }
                // Aggregate criteria
                case AGGREGATE_FIVE -> {
                    return new QuantityCriterionEvaluator<>(AggregateQuery.class, criterion);
                }
                case STAGE_MATCH -> {
                    return new AggregationCriterionEvaluator(criterion, AggregationOperators.MATCH);
                }
                case STAGE_GROUP -> {
                    return new AggregationCriterionEvaluator(criterion, AggregationOperators.GROUP);
                }
                case STAGE_SORT -> {
                    return new AggregationCriterionEvaluator(criterion, AggregationOperators.SORT);
                }
                case STAGE_PROJECT_ADDFIELDS -> {
                    return new AggregationCriterionEvaluator(criterion, AggregationOperators.PROJECT_ADDFIELDS);
                }
                case AGGREGATOR_SUM_AVG -> {
                    return new AggregationCriterionEvaluator(criterion, AggregationOperators.SUM_AVG);
                }
                case AGGREGATOR_COUNT -> {
                    return new AggregationCriterionEvaluator(criterion, AggregationOperators.COUNT);
                }
                case AGGREGATOR_MIN_MAX -> {
                    return new AggregationCriterionEvaluator(criterion, AggregationOperators.MIN_MAX);
                }
                case LOOKUP -> {
                    return new AggregationCriterionEvaluator(criterion, AggregationOperators.LOOKUP);
                }
                case COMMENT -> {
                    return new CommentCriterionEvaluator(criterion);
                }
                case UNRECOGNIZED_QUERY -> {
                    return new UnrecognizedCriterionEvaluator(criterion);
                }
                default -> {
                    throw new UnknownCriterion(name);
                }
            }
        } catch (IllegalArgumentException e) {
            throw new UnknownCriterion(name);
        }
    }
}
