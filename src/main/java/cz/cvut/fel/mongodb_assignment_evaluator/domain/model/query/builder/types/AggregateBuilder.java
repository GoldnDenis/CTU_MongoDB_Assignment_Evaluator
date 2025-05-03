package cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.builder.types;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.exceptions.IncorrectParameterSyntax;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.builder.QueryBuilder;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.parameter.DocumentParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.parameter.PipelineParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type.AggregateQueryToken;
import org.bson.BsonDocument;

import java.util.ArrayList;
import java.util.List;

public class AggregateBuilder extends QueryBuilder {
    private final List<BsonDocument> aggregationPipeline;

    public AggregateBuilder() {
        aggregationPipeline = new ArrayList<>();
    }

    @Override
    public AggregateQueryToken build() {
        return new AggregateQueryToken(
                line, column,
                comment, query, type,
                operator,
                parameters, modifiers,
                collection,
                aggregationPipeline
        );
    }

    @Override
    public void visitDocumentParameter(DocumentParameter parameter) {
        aggregationPipeline.add(parameter.getDocument());
    }

    @Override
    public void visitPipelineParameter(PipelineParameter parameter) {
        if (!aggregationPipeline.isEmpty()) {
            throw new IncorrectParameterSyntax("Pipeline", parameters.size() + 1, operator);
        }
        aggregationPipeline.addAll(parameter.getDocumentList());
    }
}
