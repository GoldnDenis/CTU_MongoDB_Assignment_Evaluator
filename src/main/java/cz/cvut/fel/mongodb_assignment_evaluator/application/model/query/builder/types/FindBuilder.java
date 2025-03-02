package cz.cvut.fel.mongodb_assignment_evaluator.application.model.query.builder.types;

import cz.cvut.fel.mongodb_assignment_evaluator.application.model.query.parameter.DocumentParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.application.model.query.parameter.EmptyParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.application.model.query.builder.QueryBuilder;
import cz.cvut.fel.mongodb_assignment_evaluator.application.model.query.type.FindQuery;
import org.bson.BsonDocument;

public class FindBuilder extends QueryBuilder {
    private BsonDocument filter;
    private BsonDocument projection;
    private BsonDocument options;

    public FindBuilder() {
        this.filter = new BsonDocument();
        this.projection = new BsonDocument();
        this.options = new BsonDocument();
    }

    @Override
    public FindQuery build() {
        return new FindQuery(
                line, column,
                comment, query, type,
                operation,
                parameters, modifiers,
                collection,
                filter, projection, options
        );
    }

    @Override
    public void visitDocumentParameter(DocumentParameter parameter) {
        switch (parameters.size()) {
            case 0 -> filter = parameter.getDocument();
            case 1 -> projection = parameter.getDocument();
            case 2 -> options = parameter.getDocument();
            default -> throw new IllegalArgumentException();
        }
    }

    @Override
    public void visitEmptyParameter(EmptyParameter parameter) {
        switch (parameters.size()) {
            case 0 -> filter = new BsonDocument();
            default -> throw new IllegalArgumentException();
        }
    }
}
