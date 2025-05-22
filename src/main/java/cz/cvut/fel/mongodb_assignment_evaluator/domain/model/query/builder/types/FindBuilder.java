package cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.builder.types;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.exceptions.IncorrectParameterSyntax;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.builder.MongoQueryBuilder;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.parameter.DocumentParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.parameter.EmptyParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type.FindQuery;
import org.bson.BsonDocument;

public class FindBuilder extends MongoQueryBuilder {
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
                precedingComment, query, type,
                command,
                parameters, modifiers,
                collection, innerComments,
                filter, projection, options
        );
    }

    @Override
    public void visitDocumentParameter(DocumentParameter parameter) {
        switch (parameters.size()) {
            case 0 -> filter = parameter.getDocument();
            case 1 -> projection = parameter.getDocument();
            case 2 -> options = parameter.getDocument();
            default -> throw new IncorrectParameterSyntax("Document", parameters.size() + 1, command);
        }
    }

    @Override
    public void visitEmptyParameter(EmptyParameter parameter) {
        if (!parameters.isEmpty()) {
            throw new IncorrectParameterSyntax("Empty", parameters.size() + 1, command);
        }
        filter = new BsonDocument();
    }
}
