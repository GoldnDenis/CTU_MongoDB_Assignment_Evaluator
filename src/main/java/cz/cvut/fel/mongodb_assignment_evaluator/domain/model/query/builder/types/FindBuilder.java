package cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.builder.types;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.builder.QueryBuilder;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.parameter.DocumentParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.parameter.EmptyParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type.FindQuery;
import org.bson.BsonDocument;

public class FindBuilder extends QueryBuilder {
    private BsonDocument filter;
    private BsonDocument projection;
    private BsonDocument options;

    public FindBuilder() {
        filter = new BsonDocument();
        projection = new BsonDocument();
        options = new BsonDocument();
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
            default -> throw new IllegalArgumentException(); // todo make an exception
        }
    }

    @Override
    public void visitEmptyParameter(EmptyParameter parameter) {
        if (!parameters.isEmpty()) {
            throw new IllegalArgumentException(); // todo make an exception
        }
        filter = new BsonDocument();
    }
}
