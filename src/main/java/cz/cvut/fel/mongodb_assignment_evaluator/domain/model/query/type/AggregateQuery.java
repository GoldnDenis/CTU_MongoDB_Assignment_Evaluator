package cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.enums.*;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.modifier.QueryModifier;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.parameter.QueryParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.criteria.bson.BsonDocumentChecker;
import lombok.Getter;
import org.bson.BsonDocument;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Getter
public class AggregateQuery extends Query {
    private final List<BsonDocument> aggregationPipeline;
//    private final BsonDocument options;

    public AggregateQuery(int lineNumber, int columnNumber, String comment, String query, Operators type, String operator,
                          List<QueryParameter> parameters, List<QueryModifier> modifiers, String collection,
                          List<BsonDocument> aggregationPipeline) {
        super(lineNumber, columnNumber, comment, query, type, operator, collection, parameters, modifiers);
        this.aggregationPipeline = new ArrayList<>(aggregationPipeline);
    }

//    public AggregateQuery(int lineNumber, int columnNumber, String comment, String query, Operators type, String operator,
//                          List<QueryParameter> parameters, List<QueryModifier> modifiers, String collection,
//                          List<BsonDocument> aggregationPipeline, BsonDocument options) {
//        super(lineNumber, columnNumber, comment, query, type, operator, collection, parameters, modifiers);
//        this.aggregationPipeline = new ArrayList<>(aggregationPipeline);
//        this.options = options;
//    }

    public boolean containsAggregations(Aggregations aggregations) {
        Stream<String> aggregationStream = aggregations.getAggregations().stream();
        int level = aggregations.getLevel();
        if (level == 0) {
            return aggregationStream.anyMatch(
                    aggregator ->
                            aggregationPipeline.stream()
                                    .anyMatch(doc -> BsonDocumentChecker.getRecursive(doc, aggregator).isPresent())
            );
        }
        return aggregationStream.anyMatch(
                stage ->
                        aggregationPipeline.stream()
                                .anyMatch(doc -> BsonDocumentChecker.getRecursive(doc, stage, level).isPresent())
        );
    }

//    public boolean containsAggregator(Aggregators aggregator) {
//        return aggregationPipeline.stream()
//                .anyMatch(d -> BsonDocumentChecker.getRecursive(d, aggregator.getValue()).isPresent());
//    }
//
//    public boolean containsStage(Stages stage) {
//        return aggregationPipeline.stream()
//                .anyMatch(d -> BsonDocumentChecker.getRecursive(d, stage.getValue(), stage.getLevel()).isPresent());
//    }

    @Override
    public boolean isTrivial() {
        return aggregationPipeline.isEmpty() ||
                aggregationPipeline.stream()
                .allMatch(BsonDocument::isEmpty);
    }
}
