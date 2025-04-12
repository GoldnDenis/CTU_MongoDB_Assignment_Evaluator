package cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.builder.types;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.builder.QueryBuilder;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.parameter.DocumentParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.parameter.PipelineParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type.UpdateQuery;
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
    public UpdateQuery build() {
        return new UpdateQuery(
                line, column,
                comment, query, type,
                operation,
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
            default -> throw new IllegalArgumentException(); // todo make an exception
        }
    }

    @Override
    public void visitPipelineParameter(PipelineParameter parameter) {
        if (!updateDocuments.isEmpty()) {
            throw new IllegalArgumentException(); // todo make an exception
        }
        updateDocuments.addAll(parameter.getDocumentList());
    }
}
