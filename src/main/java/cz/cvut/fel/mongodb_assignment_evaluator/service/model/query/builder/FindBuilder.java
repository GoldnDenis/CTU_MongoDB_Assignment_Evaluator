package cz.cvut.fel.mongodb_assignment_evaluator.service.model.query.builder;

import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.*;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.visitor.QueryParameterVisitor;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.query.type.FindQuery;

public class FindBuilder extends QueryBuilder {
    private DocumentParameter filter;
    private DocumentParameter projection;
    private DocumentParameter options;

    public FindBuilder() {
        this.filter = new DocumentParameter();
        this.projection = new DocumentParameter();
        this.options = new DocumentParameter();
    }

    @Override
    public FindQuery build() {
        return new FindQuery(
                lineNumber, columnNumber,
                comment, query, type,
                operation,
                parameters, modifiers,
                collection,
                filter, projection, options
        );
    }

    @Override
    public void visitDocumentParameter(DocumentParameter parameter) {
        switch (parameters.size()) {
            case 0: filter = parameter;
            case 1: projection = parameter;
            case 2: options = parameter;
            default: throw new IllegalArgumentException();
        }
    }
}
