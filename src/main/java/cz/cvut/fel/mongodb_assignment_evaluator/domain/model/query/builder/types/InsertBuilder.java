package cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.builder.types;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.enums.MongoCommands;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.exceptions.IncorrectParameterSyntax;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.builder.MongoQueryBuilder;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.parameter.ArrayParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.parameter.DocumentParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type.InsertQuery;
import org.bson.BsonDocument;

import java.util.ArrayList;
import java.util.List;

public class InsertBuilder extends MongoQueryBuilder {
    private final List<BsonDocument> insertedDocuments;
    private BsonDocument options;

    public InsertBuilder() {
        insertedDocuments = new ArrayList<>();
        options = new BsonDocument();
    }

    @Override
    public InsertQuery build() {
        return new InsertQuery(
                line, column,
                precedingComment, query, type,
                command,
                parameters, modifiers,
                collection, innerComments,
                insertedDocuments, options
        );
    }

    @Override
    public void visitDocumentParameter(DocumentParameter parameter) {
        switch (parameters.size()) {
            case 0 -> insertedDocuments.add(parameter.getDocument());
            case 1 -> options = parameter.getDocument();
            default -> throw new IncorrectParameterSyntax("Document", parameters.size() + 1, command);
        }
    }

    @Override
    public void visitArrayParameter(ArrayParameter parameter) {
        if (command.equalsIgnoreCase(MongoCommands.INSERT_ONE.getCommand()) || !insertedDocuments.isEmpty()) {
            throw new IncorrectParameterSyntax("Array", parameters.size() + 1, command);
        }
        insertedDocuments.addAll(parameter.getDocumentList());
    }
}
