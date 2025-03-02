package cz.cvut.fel.mongodb_assignment_evaluator.application.model.query.builder.types;

import cz.cvut.fel.mongodb_assignment_evaluator.application.model.query.parameter.DocumentParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.application.model.query.builder.QueryBuilder;
import cz.cvut.fel.mongodb_assignment_evaluator.application.model.query.type.ReplaceOneQuery;
import org.bson.BsonDocument;

public class ReplaceOneBuilder extends QueryBuilder {
    private BsonDocument filter;
    private BsonDocument replacement;
    private BsonDocument options;

    public ReplaceOneBuilder() {
        this.filter = new BsonDocument();
        this.replacement = new BsonDocument();
        this.options = new BsonDocument();
    }

    @Override
    public ReplaceOneQuery build() {
        return new ReplaceOneQuery(
                line, column,
                comment, query, type,
                operation,
                parameters, modifiers,
                collection,
                filter, replacement, options
        );
    }

    @Override
    public void visitDocumentParameter(DocumentParameter parameter) {
        switch (parameters.size()) {
            case 0 -> filter = parameter.getDocument();
            case 1 -> replacement = parameter.getDocument();
            case 2 -> options = parameter.getDocument();
            default -> throw new IllegalArgumentException();
        }
    }
}
