package cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.grader.criteria.factory;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.enums.*;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.grader.criteria.EvaluationCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.grader.criteria.aggregate.AggregationCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.grader.criteria.find.FindSelectorCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.grader.criteria.find.FindTypeCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.grader.criteria.find.ProjectionCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.grader.criteria.general.*;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.grader.criteria.insert.InsertTypeCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.grader.criteria.update.UpdateGroupCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.grader.criteria.update.UpdatePatternCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.grader.criteria.update.UpsertCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.exceptions.UnknownCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.entity.Criterion;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type.*;
import org.bson.BsonType;

public class CriteriaFactory {
    public EvaluationCriterion<? extends QueryToken> createEvaluationCriterion(Criterion criterion) {
        String name = criterion.getName().toUpperCase();
        try {
            Criteria foundCriterion = Criteria.valueOf(name);
            switch (foundCriterion) {
                // CreateCollection criteria
                case CREATE_ONE_COLLECTION -> {
                    return new OperatorEvalCriterion<>(CreateCollectionQueryToken.class, criterion, Operators.CREATE_COLLECTION);
                }
                // Insert criteria
                case INSERT_TEN_DOCUMENTS -> {
                    return new QuantityCriterion<>(InsertQueryToken.class, criterion);
                }
                case INSERT_DOCUMENT_EMBEDDED -> {
                    return new InsertTypeCriterion(criterion, BsonType.DOCUMENT);
                }
                case INSERT_DOCUMENT_ARRAY -> {
                    return new InsertTypeCriterion(criterion, BsonType.ARRAY);
                }
                case INSERT_ONE_USED -> {
                    return new OperatorEvalCriterion<>(InsertQueryToken.class, criterion, Operators.INSERT_ONE);
                }
                case INSERT_MANY_USED -> {
                    return new OperatorEvalCriterion<>(InsertQueryToken.class, criterion, Operators.INSERT_MANY);
                }
                // Replace criteria
                case REPLACE_VALUE -> {
                    return new QuantityCriterion<>(ReplaceQueryToken.class, criterion);
                }
                // Update criteria
                case UPDATE_ONE_DOCUMENT -> {
                    return new UpdateGroupCriterion(criterion, UpdateGroups.UPDATE, Operators.UPDATE_ONE);
                }
                case UPDATE_MANY_FORMULA -> {
                    return new UpdateGroupCriterion(criterion, UpdateGroups.FORMULA, Operators.UPDATE_MANY);
                }
                case UPDATE_ADD_FIELD -> {
                    return new UpdateGroupCriterion(criterion, UpdateGroups.ADD);
                }
                case UPDATE_REMOVE_FIELD -> {
                    return new UpdateGroupCriterion(criterion, UpdateGroups.REMOVE);
                }
                case UPDATE_NESTED_DOCUMENT -> {
                    return new UpdatePatternCriterion(criterion, RegularExpressions.NESTED_FIELD, UpdateGroups.ADD);
                }
                case UPDATE_ARRAY -> {
                    return new UpdateGroupCriterion(criterion, UpdateGroups.ARRAY);
                }
                case UPSERT_USED -> {
                    return new UpsertCriterion(criterion, true);
                }
                // Find criteria
                case FIND_FIVE -> {
                    return new QuantityCriterion<>(FindQueryToken.class, criterion);
                }
                case FIND_LOGICAL_OPERATOR -> {
                    return new FindSelectorCriterion(criterion, QuerySelectors.LOGICAL);
                }
                case FIND_COMPARISON -> {
                    return new FindSelectorCriterion(criterion, QuerySelectors.COMPARISON);
                }
                case FIND_ARRAY_OPERATOR -> {
                    return new FindSelectorCriterion(criterion, QuerySelectors.ARRAY);
                }
                case FIND_DATE_OPERATOR -> {
                    return new FindTypeCriterion(criterion, BsonType.DATE_TIME);
                }
                case POSITIVE_PROJECTION -> {
                    return new ProjectionCriterion(criterion, true);
                }
                case NEGATIVE_PROJECTION -> {
                    return new ProjectionCriterion(criterion, false);
                }
                case SORT_MODIFIER -> {
                    return new ModifierOperatorCriterion<>(FindQueryToken.class, criterion, Operators.SORT);
                }
                // Aggregate criteria
                case AGGREGATE_FIVE -> {
                    return new QuantityCriterion<>(AggregateQueryToken.class, criterion);
                }
                case STAGE_MATCH -> {
                    return new AggregationCriterion(criterion, Aggregations.MATCH);
                }
                case STAGE_GROUP -> {
                    return new AggregationCriterion(criterion, Aggregations.GROUP);
                }
                case STAGE_SORT -> {
                    return new AggregationCriterion(criterion, Aggregations.SORT);
                }
                case STAGE_PROJECT_ADDFIELDS -> {
                    return new AggregationCriterion(criterion, Aggregations.PROJECT_ADDFIELDS);
                }
                case AGGREGATOR_SUM_AVG -> {
                    return new AggregationCriterion(criterion, Aggregations.SUM_AVG);
                }
                case AGGREGATOR_COUNT -> {
                    return new AggregationCriterion(criterion, Aggregations.COUNT);
                }
                case AGGREGATOR_MIN_MAX -> {
                    return new AggregationCriterion(criterion, Aggregations.MIN_MAX);
                }
                case LOOKUP -> {
                    return new AggregationCriterion(criterion, Aggregations.LOOKUP);
                }
                case COMMENT -> {
                    return new CommentEvalCriterion(criterion);
                }
                case UNRECOGNIZED_QUERY -> {
                    return new UnrecognizedCriterion(criterion);
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
