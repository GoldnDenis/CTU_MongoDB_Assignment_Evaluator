package cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.builder.types;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.builder.QueryBuilder;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.parameter.DocumentParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.parameter.PipelineParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type.InsertQuery;
import org.bson.BsonDocument;

import java.util.ArrayList;
import java.util.List;

public class InsertBuilder extends QueryBuilder {
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
                comment, query, type,
                operation,
                parameters, modifiers,
                collection, insertedDocuments, options
        );
    }

    @Override
    public void visitDocumentParameter(DocumentParameter parameter) {
        switch (parameters.size()) {
            case 0 -> insertedDocuments.add(parameter.getDocument());
            case 1 -> options = parameter.getDocument();
            default -> throw new IllegalArgumentException(); // todo make an exception
        }
    }

    @Override
    public void visitPipelineParameter(PipelineParameter parameter) {
        if (!insertedDocuments.isEmpty()) {
            throw new IllegalArgumentException(); // todo make an exception
        }
        insertedDocuments.addAll(parameter.getDocumentList());
    }
}
