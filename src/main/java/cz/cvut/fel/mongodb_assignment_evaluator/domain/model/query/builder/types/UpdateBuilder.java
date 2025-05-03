package cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.builder.types;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.exceptions.IncorrectParameterSyntax;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.builder.QueryBuilder;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.parameter.DocumentParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.parameter.PipelineParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type.UpdateQueryToken;
import org.bson.BsonDocument;

import java.util.ArrayList;
import java.util.List;

public class UpdateBuilder extends QueryBuilder {
    private BsonDocument filter;
    private final List<BsonDocument> updateDocuments;
    private BsonDocument options;

    public UpdateBuilder() {
        filter = new BsonDocument();
        updateDocuments = new ArrayList<>();
        options = new BsonDocument();
    }

    @Override
    public UpdateQueryToken build() {
        return new UpdateQueryToken(
                line, column,
                comment, query, type,
                operator,
                parameters, modifiers,
                collection,
                filter, updateDocuments, options
        );
    }

    @Override
    public void visitDocumentParameter(DocumentParameter parameter) {
        switch (parameters.size()) {
            case 0 -> filter = parameter.getDocument();
            case 1 -> updateDocuments.add(parameter.getDocument());
            case 2 -> options = parameter.getDocument();
            default -> throw new IncorrectParameterSyntax("Document", parameters.size() + 1, operator);
        }
    }

    @Override
    public void visitPipelineParameter(PipelineParameter parameter) {
        if (!updateDocuments.isEmpty()) {
            throw new IncorrectParameterSyntax("Pipeline", parameters.size() + 1, operator);
        }
        updateDocuments.addAll(parameter.getDocumentList());
    }
}
