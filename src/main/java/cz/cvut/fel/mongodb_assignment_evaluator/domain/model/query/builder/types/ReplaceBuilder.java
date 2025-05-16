package cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.builder.types;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.exceptions.IncorrectParameterSyntax;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.builder.QueryBuilder;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.parameter.DocumentParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type.ReplaceQueryToken;
import org.bson.BsonDocument;

public class ReplaceBuilder extends QueryBuilder {
    private BsonDocument filter;
    private BsonDocument replacement;
    private BsonDocument options;

    public ReplaceBuilder() {
        filter = new BsonDocument();
        replacement = new BsonDocument();
        options = new BsonDocument();
    }

    @Override
    public ReplaceQueryToken build() {
        return new ReplaceQueryToken(
                line, column,
                precedingComment, query, type,
                operator,
                parameters, modifiers,
                collection, innerComments,
                filter, replacement, options
        );
    }

    @Override
    public void visitDocumentParameter(DocumentParameter parameter) {
        switch (parameters.size()) {
            case 0 -> filter = parameter.getDocument();
            case 1 -> replacement = parameter.getDocument();
            case 2 -> options = parameter.getDocument();
            default -> throw new IncorrectParameterSyntax("Document", parameters.size() + 1, operator);
        }
    }
}
