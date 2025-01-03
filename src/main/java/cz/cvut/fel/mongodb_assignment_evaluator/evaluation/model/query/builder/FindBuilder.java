package cz.cvut.fel.mongodb_assignment_evaluator.evaluation.model.query.builder;

import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.model.parameter.DocumentParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.model.parameter.EmptyParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.model.query.type.FindQuery;

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
            case 0 -> filter = parameter;
            case 1 -> projection = parameter;
            case 2 -> options = parameter;
            default -> throw new IllegalArgumentException();
        }
    }

    @Override
    public void visitEmptyParameter(EmptyParameter parameter) {
        switch (parameters.size()) {
            case 0 -> filter = new DocumentParameter();
            default -> throw new IllegalArgumentException();
        }
    }
}
