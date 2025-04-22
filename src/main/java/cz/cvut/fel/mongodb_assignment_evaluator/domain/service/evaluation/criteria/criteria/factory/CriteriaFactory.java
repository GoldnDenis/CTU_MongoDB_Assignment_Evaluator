package cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.criteria.criteria.factory;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.enums.*;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.exceptions.UnknownCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type.*;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.criteria.criteria.EvaluationCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.criteria.criteria.aggregate.AggregationCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.criteria.criteria.find.FindSelectorCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.criteria.criteria.find.FindTypeCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.criteria.criteria.find.ProjectionCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.criteria.criteria.general.*;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.criteria.criteria.insert.InsertTypeCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.criteria.criteria.update.UpdateGroupCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.criteria.criteria.update.UpdatePatternCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.criteria.criteria.update.UpsertCriterion;
import org.bson.BsonType;

public class CriteriaFactory {
    public EvaluationCriterion<? extends Query> createEvaluationCriterion(String name) {
        try {
            Criteria criterion = Criteria.valueOf(name.toUpperCase());
            int priority = criterion.ordinal();
            switch (criterion) {
                // CreateCollection criteria
                case CREATE_ONE_COLLECTION -> {
                    return new OperatorEvalCriterion<>(CreateCollectionQuery.class, priority, Operators.CREATE_COLLECTION);
                }
                // Insert criteria
                case INSERT_TEN_DOCUMENTS -> {
                    return new QuantityCriterion<>(InsertQuery.class, priority);
                }
                case INSERT_DOCUMENT_EMBEDDED -> {
                    return new InsertTypeCriterion(priority, BsonType.DOCUMENT);
                }
                case INSERT_DOCUMENT_ARRAY -> {
                    return new InsertTypeCriterion(priority, BsonType.ARRAY);
                }
                case INSERT_ONE_USED -> {
                    return new OperatorEvalCriterion<>(InsertQuery.class, priority, Operators.INSERT_ONE);
                }
                case INSERT_MANY_USED -> {
                    return new OperatorEvalCriterion<>(InsertQuery.class, priority, Operators.INSERT_MANY);
                }
                // Replace criteria
                case REPLACE_VALUE -> {
                    return new QuantityCriterion<>(ReplaceOneQuery.class, priority);
                }
                // Update criteria
                case UPDATE_ONE_DOCUMENT -> {
                    return new UpdateGroupCriterion(priority, UpdateGroups.UPDATE, Operators.UPDATE_ONE);
                }
                case UPDATE_MANY_FORMULA -> {
                    return new UpdateGroupCriterion(priority, UpdateGroups.FORMULA, Operators.UPDATE_MANY);
                }
                case UPDATE_ADD_FIELD -> {
                    return new UpdateGroupCriterion(priority, UpdateGroups.ADD);
                }
                case UPDATE_REMOVE_FIELD -> {
                    return new UpdateGroupCriterion(priority, UpdateGroups.REMOVE);
                }
                case UPDATE_NESTED_DOCUMENT -> {
                    return new UpdatePatternCriterion(priority, RegularExpressions.NESTED_FIELD, UpdateGroups.ADD);
                }
                case UPDATE_ARRAY -> {
                    return new UpdateGroupCriterion(priority, UpdateGroups.ARRAY);
                }
                case UPSERT_USED -> {
                    return new UpsertCriterion(priority, true);
                }
                // Find criteria
                case FIND_FIVE -> {
                    return new QuantityCriterion<>(FindQuery.class, priority);
                }
                case FIND_LOGICAL_OPERATOR -> {
                    return new FindSelectorCriterion(priority, QuerySelectors.LOGICAL);
                }
                case FIND_COMPARISON -> {
                    return new FindSelectorCriterion(priority, QuerySelectors.COMPARISON);
                }
                case FIND_ARRAY_OPERATOR -> {
                    return new FindSelectorCriterion(priority, QuerySelectors.ARRAY);
                }
                case FIND_DATE_OPERATOR -> {
//                    return new FindTypeCriterion(priority, RegularExpressions.DATE_FIELD);
                    return new FindTypeCriterion(priority, BsonType.DATE_TIME);
                }
                case POSITIVE_PROJECTION -> {
                    return new ProjectionCriterion(priority, true);
                }
                case NEGATIVE_PROJECTION -> {
                    return new ProjectionCriterion(priority, false);
                }
                case SORT_MODIFIER -> {
                    return new ModifierOperatorCriterion<>(FindQuery.class, priority, Operators.SORT);
                }
                // Aggregate criteria
                case AGGREGATE_FIVE -> {
                    return new QuantityCriterion<>(AggregateQuery.class, priority);
                }
                case STAGE_MATCH -> {
                    return new AggregationCriterion(priority, Aggregations.MATCH);
                }
                case STAGE_GROUP -> {
                    return new AggregationCriterion(priority, Aggregations.GROUP);
                }
                case STAGE_SORT -> {
                    return new AggregationCriterion(priority, Aggregations.SORT);
                }
                case STAGE_PROJECT_ADDFIELDS -> {
                    return new AggregationCriterion(priority, Aggregations.PROJECT_ADDFIELDS);
                }
                case AGGREGATOR_SUM_AVG -> {
                    return new AggregationCriterion(priority, Aggregations.SUM_AVG);
                }
                case AGGREGATOR_COUNT -> {
                    return new AggregationCriterion(priority, Aggregations.COUNT);
                }
                case AGGREGATOR_MIN_MAX -> {
                    return new AggregationCriterion(priority, Aggregations.MIN_MAX);
                }
                case LOOKUP -> {
                    return new AggregationCriterion(priority, Aggregations.LOOKUP);
                }
                case UNRECOGNIZED_QUERY -> {
                    return new UnrecognizedCriterion(priority);
                }
                case COMMENT -> {
                    return new CommentEvalCriterion(priority);
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
