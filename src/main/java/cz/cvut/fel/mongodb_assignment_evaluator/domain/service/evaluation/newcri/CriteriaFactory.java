package cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.newcri;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.enums.RegularExpressions;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type.*;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.newcri.criteria.aggregate.AggregationCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.newcri.criteria.find.FindSelectorCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.newcri.criteria.find.FindTypeCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.newcri.criteria.find.ProjectionCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.newcri.criteria.general.CommentEvalCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.newcri.criteria.general.ModifierOperatorCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.newcri.criteria.general.OperatorEvalCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.newcri.criteria.general.QuantityCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.newcri.criteria.insert.InsertTypeCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.newcri.criteria.insert.InsertQuantityCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.newcri.criteria.update.UpdateGroupCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.newcri.criteria.update.UpdatePatternCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.newcri.criteria.update.UpsertCriterion;
import org.bson.BsonType;

import java.util.List;
import java.util.Set;

public class CriteriaFactory {
    public EvaluationCriterion<? extends Query> createEvaluationCriterion(String name) {
        switch (name.toUpperCase()) {
            // CreateCollection criteria
            case "CREATE_ONE_COLLECTION" -> {
                return new OperatorEvalCriterion<>(CreateCollectionQuery.class, "createCollection");
            }
            // Insert criteria
            case "INSERT_TEN_DOCUMENTS" -> {
                return new InsertQuantityCriterion();
            }
            case "INSERT_DOCUMENT_EMBEDDED" -> {
                return new InsertTypeCriterion(BsonType.DOCUMENT);
            }
            case "INSERT_DOCUMENT_ARRAY" -> {
                return new InsertTypeCriterion(BsonType.ARRAY);
            }
            case "INSERT_ONE_USED" -> {
                return new OperatorEvalCriterion<>(InsertQuery.class, "insertOne");
            }
            case "INSERT_MANY_USED" -> {
                return new OperatorEvalCriterion<>(InsertQuery.class, "insertMany");
            }
            // Replace criteria
            case "REPLACE_VALUE" -> {
                return new QuantityCriterion<>(ReplaceOneQuery.class);
            }
             // Update criteria
            case "UPDATE_ONE_DOCUMENT" -> {
                return new UpdateGroupCriterion("updateOne", Set.of("$set", "$rename", "$mul", "$inc","$min", "$max", "$currentDate"));
            }
            case "UPDATE_MANY_FORMULA" -> {
                return new UpdateGroupCriterion("updateMany", Set.of("$mul", "$inc"));
            }
            case "UPDATE_ADD_FIELD" -> {
                return new UpdateGroupCriterion(Set.of("$set", "$mul", "$inc"));
            }
            case "UPDATE_REMOVE_FIELD" -> {
                return new UpdateGroupCriterion(Set.of("$unset"));
            }
            case "UPDATE_NESTED_DOCUMENT" -> {
                return new UpdatePatternCriterion(RegularExpressions.NESTED_FIELD.getRegex());
            }
            case "UPDATE_ARRAY_REPLACE" -> {
                return new UpdatePatternCriterion(RegularExpressions.ARRAY_FIELD.getRegex());
            }
            case "UPDATE_ARRAY_ADD" -> {
                return new UpdateGroupCriterion(Set.of("$push"));
            }
            case "UPDATE_ARRAY_REMOVE" -> {
                return new UpdateGroupCriterion(Set.of("$pull", "$pop"));
            }
            case "UPSERT_USED" -> {
                return new UpsertCriterion(true);
            }
            // Find criteria
            case "FIND_FIVE" -> {
                return new QuantityCriterion<>(FindQuery.class);
            }
            case "FIND_LOGICAL_OPERATOR" -> {
                return new FindSelectorCriterion(Set.of("$and", "$or", "not"));
            }
            case "ELEMMATCH_USED" -> {
                return new FindSelectorCriterion(Set.of("$elemMatch"));
            }
            case "POSITIVE_PROJECTION" -> {
                return new ProjectionCriterion(true);
            }
            case "NEGATIVE_PROJECTION" -> {
                return new ProjectionCriterion(false);
            }
            case "FIND_MODIFIER_SORT" -> {
                return new ModifierOperatorCriterion<>(FindQuery.class, "sort");
            }
//            case "FIND_VALUE" -> { //todo hard to check without actually looking for documents
//                return new
//            }
            case "FIND_CONDITION" -> {
                return new FindSelectorCriterion(Set.of("$gte", "$gt", "$lte", "$lt"));
            }
//            case "FIND_DATE_COMPARISON" -> { //todo hard to check without actually looking for documents
//                return new
//            }
            case "FIND_ARRAY" -> {
                return new FindTypeCriterion(BsonType.ARRAY, Set.of("$size"));
            }
            case "FIND_ARRAY_VALUE" -> {
                return new FindSelectorCriterion(Set.of("$in", "$all", "$elemMatch"));
            }
            // Aggregate criteria
            case "AGGREGATE_FIVE" -> {
                return new QuantityCriterion<>(AggregateQuery.class);
            }
//            case "INTERLINK" -> { // todo without checking other documents -> the same as lookup criterion
//                return new AggregationCriterion(List.of("$lookup"), 1);
//            }
            case "STAGE_MATCH" -> {
                return new AggregationCriterion(Set.of("$match"), 1);
            }
            case "STAGE_GROUP" -> {
                return new AggregationCriterion(Set.of("$group"), 1);
            }
            case "STAGE_SORT" -> {
                return new AggregationCriterion(Set.of("$sort"), 1);
            }
            case "STAGE_PROJECT_ADDFIELDS" -> {
                return new AggregationCriterion(Set.of("$project", "$addFields"), 1);
            }
            case "STAGE_SKIP" -> {
                return new AggregationCriterion(Set.of("$skip"), 1);
            }
            case "STAGE_LIMIT" -> {
                return new AggregationCriterion(Set.of("$limit"), 1);
            }
            case "STAGE_LOOKUP" -> {
                return new AggregationCriterion(Set.of("$lookup"), 1);
            }
            case "AGGREGATOR_SUM_AVG" -> {
                return new AggregationCriterion(Set.of("$sum", "$avg"));
            }
            case "AGGREGATOR_COUNT" -> {
                return new AggregationCriterion(Set.of("$count"));
            }
            case "AGGREGATOR_MIN_MAX" -> {
                return new AggregationCriterion(Set.of("$min", "$max"));
            }
            case "AGGREGATOR_FIRST_LAST" -> {
                return new AggregationCriterion(Set.of("$first", "$last"));
            }
            // General criteria
            case "COMMENT" -> {
                return new CommentEvalCriterion();
            }
            default -> {
                // evaluator for the criterion is not defined TODO
                throw new IllegalArgumentException("Invalid criteria name: " + name);
            }
        }
    }
}
