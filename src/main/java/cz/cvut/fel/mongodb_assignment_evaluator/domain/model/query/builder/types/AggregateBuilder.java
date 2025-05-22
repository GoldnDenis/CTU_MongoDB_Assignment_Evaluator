package cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.builder.types;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.exceptions.IncorrectParameterSyntax;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.builder.MongoQueryBuilder;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.parameter.ArrayParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.parameter.DocumentParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type.AggregateQuery;
import org.bson.BsonDocument;

import java.util.ArrayList;
import java.util.List;

public class AggregateBuilder extends MongoQueryBuilder {
    private final List<BsonDocument> aggregationPipeline;

    public AggregateBuilder() {
        aggregationPipeline = new ArrayList<>();
    }

    @Override
    public AggregateQuery build() {
        return new AggregateQuery(
                line, column,
                precedingComment, query, type,
                command,
                parameters, modifiers,
                collection, innerComments,
                aggregationPipeline
        );
    }

    @Override
    public void visitDocumentParameter(DocumentParameter parameter) {
        aggregationPipeline.add(parameter.getDocument());
    }

    @Override
    public void visitArrayParameter(ArrayParameter parameter) {
        if (!aggregationPipeline.isEmpty()) {
            throw new IncorrectParameterSyntax("Array", parameters.size() + 1, command);
        }
        aggregationPipeline.addAll(parameter.getDocumentList());
    }
}
