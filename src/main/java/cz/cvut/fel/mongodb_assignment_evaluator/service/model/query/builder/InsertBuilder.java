package cz.cvut.fel.mongodb_assignment_evaluator.service.model.query.builder;

import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.*;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.visitor.QueryParameterVisitor;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.query.type.InsertQuery;

import java.util.ArrayList;
import java.util.List;

public class InsertBuilder extends QueryBuilder {
    private final List<DocumentParameter> insertedDocuments;
    private DocumentParameter options;

    public InsertBuilder() {
        insertedDocuments = new ArrayList<>();
        options = new DocumentParameter();
    }

    @Override
    public InsertQuery build() {
        return new InsertQuery(
                lineNumber, columnNumber,
                comment, query, type,
                operation,
                parameters, modifiers,
                collection, insertedDocuments, options
        );
    }

    @Override
    public void visitDocumentParameter(DocumentParameter parameter) {
        switch (parameters.size()) {
            case 0 -> insertedDocuments.add(parameter);
            case 1 -> options = parameter;
            default -> throw new IllegalArgumentException();
        }
    }

    @Override
    public void visitPipelineParameter(PipelineParameter parameter) {
        switch (parameters.size()) {
            case 0 -> insertedDocuments.addAll(parameter.getParameterList());
            default -> throw new IllegalArgumentException();
        }
    }
}
