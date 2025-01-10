package cz.cvut.fel.mongodb_assignment_evaluator.application.model.query.builder.types;

import cz.cvut.fel.mongodb_assignment_evaluator.application.model.parameter.DocumentParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.application.model.parameter.PipelineParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.application.model.query.builder.QueryBuilder;
import cz.cvut.fel.mongodb_assignment_evaluator.application.model.query.type.AggregateQuery;
import org.bson.BsonDocument;

import java.util.ArrayList;
import java.util.List;

public class AggregateBuilder extends QueryBuilder {
    private final List<BsonDocument> aggregationPipeline;
    private BsonDocument options;

    public AggregateBuilder() {
        this.aggregationPipeline = new ArrayList<>();
        this.options = new BsonDocument();
    }

    @Override
    public AggregateQuery build() {
        return new AggregateQuery(
                line, column,
                comment, query, type,
                operation,
                parameters, modifiers,
                collection,
                aggregationPipeline, options
        );
    }

    @Override
    public void visitDocumentParameter(DocumentParameter parameter) {
        switch (parameters.size()) {
            case 1 -> options = parameter.getDocument();
            default -> throw new IllegalArgumentException();
        }
    }

    @Override
    public void visitPipelineParameter(PipelineParameter parameter) {
        switch (parameters.size()) {
            case 0 -> aggregationPipeline.addAll(parameter.getDocumentList());
            default -> throw new IllegalArgumentException();
        }
    }
}
