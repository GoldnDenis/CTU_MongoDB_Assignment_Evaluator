package cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.enums.Aggregations;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.enums.Operators;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.grader.bson.BsonDocumentChecker;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.modifier.QueryModifier;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.parameter.QueryParameter;
import lombok.Getter;
import org.bson.BsonDocument;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Getter
public class AggregateQueryToken extends QueryToken {
    private final List<BsonDocument> aggregationPipeline;

    public AggregateQueryToken(int lineNumber, int columnNumber, String precedingComment, String query, Operators type, String operator,
                               List<QueryParameter> parameters, List<QueryModifier> modifiers, String collection, List<String> innerComments,
                               List<BsonDocument> aggregationPipeline) {
        super(lineNumber, columnNumber, precedingComment, query, type, operator, collection, parameters, modifiers, innerComments);
        this.aggregationPipeline = new ArrayList<>(aggregationPipeline);
    }

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

    @Override
    public boolean isTrivial() {
        return aggregationPipeline.isEmpty() ||
                aggregationPipeline.stream()
                        .allMatch(BsonDocument::isEmpty);
    }
}
