package cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.builder.types;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.exceptions.IncorrectParameterSyntax;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.builder.MongoQueryBuilder;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.parameter.ArrayParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.parameter.DocumentParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type.UpdateQuery;
import org.bson.BsonDocument;

import java.util.ArrayList;
import java.util.List;

public class UpdateBuilder extends MongoQueryBuilder {
    private BsonDocument filter;
    private final List<BsonDocument> updateDocuments;
    private BsonDocument options;

    public UpdateBuilder() {
        filter = new BsonDocument();
        updateDocuments = new ArrayList<>();
        options = new BsonDocument();
    }

    @Override
    public UpdateQuery build() {
        return new UpdateQuery(
                line, column,
                precedingComment, query, type,
                command,
                parameters, modifiers,
                collection, innerComments,
                filter, updateDocuments, options
        );
    }

    @Override
    public void visitDocumentParameter(DocumentParameter parameter) {
        switch (parameters.size()) {
            case 0 -> filter = parameter.getDocument();
            case 1 -> updateDocuments.add(parameter.getDocument());
            case 2 -> options = parameter.getDocument();
            default -> throw new IncorrectParameterSyntax("Document", parameters.size() + 1, command);
        }
    }

    @Override
    public void visitArrayParameter(ArrayParameter parameter) {
        if (!updateDocuments.isEmpty()) {
            throw new IncorrectParameterSyntax("Array", parameters.size() + 1, command);
        }
        updateDocuments.addAll(parameter.getDocumentList());
    }
}
