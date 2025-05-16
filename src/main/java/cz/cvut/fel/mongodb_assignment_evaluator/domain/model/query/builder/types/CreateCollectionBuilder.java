package cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.builder.types;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.exceptions.IncorrectParameterSyntax;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.builder.QueryBuilder;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.parameter.DocumentParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.parameter.StringParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type.CreateCollectionQueryToken;
import org.bson.BsonDocument;


public class CreateCollectionBuilder extends QueryBuilder {
    private String collectionName;
    private BsonDocument options;

    public CreateCollectionBuilder() {
        collectionName = "";
        options = new BsonDocument();
    }

    @Override
    public CreateCollectionQueryToken build() {
        return new CreateCollectionQueryToken(
                line, column,
                precedingComment, query, type,
                operator, "",
                parameters, modifiers, innerComments,
                collectionName, options
        );
    }

    @Override
    public void visitDocumentParameter(DocumentParameter parameter) {
        if (collectionName.isBlank()) {
            throw new IncorrectParameterSyntax("'.createCollection' cannot have a blank collection name");
        }
        options = parameter.getDocument();
    }

    @Override
    public void visitStringParameter(StringParameter parameter) {
        if (!collectionName.isBlank()) {
            throw new IncorrectParameterSyntax("String", parameters.size() + 1, operator);
        }
        collectionName = parameter.getValue();
    }
}
