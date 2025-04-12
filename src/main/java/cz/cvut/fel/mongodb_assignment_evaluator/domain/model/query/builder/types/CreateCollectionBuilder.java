package cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.builder.types;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.builder.QueryBuilder;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.parameter.DocumentParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.parameter.StringParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type.CreateCollectionQuery;
import org.bson.BsonDocument;


public class CreateCollectionBuilder extends QueryBuilder {
    private String collectionName;
    private BsonDocument options;

    public CreateCollectionBuilder() {
        collectionName = "";
        options = new BsonDocument();
    }

    @Override
    public CreateCollectionQuery build() {
        return new CreateCollectionQuery(
                line, column,
                comment, query, type,
                operation, "",
                parameters, modifiers,
                collectionName, options
        );
    }

    @Override
    public void visitDocumentParameter(DocumentParameter parameter) {
        if (collectionName.isBlank()) {
            throw new IllegalArgumentException(); // todo make an exception
        }
        options = parameter.getDocument();
    }

    @Override
    public void visitStringParameter(StringParameter parameter) {
        if (!collectionName.isBlank()) {
            throw new IllegalArgumentException(); // todo make an exception
        }
        collectionName = parameter.getValue();
    }
}
