package cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.builder.types;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.exceptions.IncorrectParameterSyntax;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.builder.MongoQueryBuilder;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.parameter.DocumentParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.parameter.StringParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type.CreateCollectionQuery;
import org.bson.BsonDocument;


public class CreateCollectionBuilder extends MongoQueryBuilder {
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
                precedingComment, query, type,
                command, "",
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
            throw new IncorrectParameterSyntax("String", parameters.size() + 1, command);
        }
        collectionName = parameter.getValue();
    }
}
