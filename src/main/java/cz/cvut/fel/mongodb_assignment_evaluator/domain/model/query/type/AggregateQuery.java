package cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.enums.AggregationOperators;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.enums.MongoCommands;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.grader.bson.BsonDocumentChecker;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.modifier.QueryModifier;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.parameter.QueryParameter;
import lombok.Getter;
import org.bson.BsonDocument;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Getter
public class AggregateQuery extends MongoQuery {
    private final List<BsonDocument> aggregationPipeline;

    public AggregateQuery(int lineNumber, int columnNumber, String precedingComment, String query, MongoCommands type, String operator,
                          List<QueryParameter> parameters, List<QueryModifier> modifiers, String collection, List<String> innerComments,
                          List<BsonDocument> aggregationPipeline) {
        super(lineNumber, columnNumber, precedingComment, query, type, operator, collection, parameters, modifiers, innerComments);
        this.aggregationPipeline = new ArrayList<>(aggregationPipeline);
    }

    /**
     * Inspects the aggregation pipeline for provided operators
     * @param aggregationOperators
     * @return if it found the operator
     */
    public boolean containsAggregations(AggregationOperators aggregationOperators) {
        Stream<String> aggregationStream = aggregationOperators.getAggregations().stream();
        int level = aggregationOperators.getLevel();
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

    /**
     * @return all documents in the aggregation pipeline are empty
     */
    @Override
    public boolean isTrivial() {
        return aggregationPipeline.isEmpty() ||
                aggregationPipeline.stream()
                        .allMatch(BsonDocument::isEmpty);
    }
}
