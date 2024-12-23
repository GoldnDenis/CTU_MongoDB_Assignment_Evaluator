package cz.cvut.fel.mongodb_assignment_evaluator.service.model.query.builder;

import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.*;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.visitor.QueryParameterVisitor;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.query.type.UpdateQuery;

import java.util.ArrayList;
import java.util.List;

public class UpdateBuilder extends QueryBuilder {
    private DocumentParameter filter;
    private final List<DocumentParameter> updateDocuments;
    private DocumentParameter options;

    public UpdateBuilder() {
        this.filter = new DocumentParameter();
        this.updateDocuments = new ArrayList<>();
        this.options = new DocumentParameter();
    }

    @Override
    public UpdateQuery build() {
        return new UpdateQuery(
                lineNumber, columnNumber,
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
            case 0: filter = parameter;
            case 2: options = parameter;
            default: throw new IllegalArgumentException();
        }
    }

    @Override
    public void visitPipelineParameter(PipelineParameter parameter) {
        switch (parameters.size()) {
            case 1: updateDocuments.addAll(parameter.getParameterList());
            default: throw new IllegalArgumentException();
        }
    }
}
