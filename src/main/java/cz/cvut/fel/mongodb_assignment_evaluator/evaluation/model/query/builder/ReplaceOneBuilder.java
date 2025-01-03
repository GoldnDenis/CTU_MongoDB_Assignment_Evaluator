package cz.cvut.fel.mongodb_assignment_evaluator.evaluation.model.query.builder;

import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.model.parameter.DocumentParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.model.query.type.ReplaceOneQuery;

public class ReplaceOneBuilder extends QueryBuilder {
    private DocumentParameter filter;
    private DocumentParameter replacement;
    private DocumentParameter options;

    public ReplaceOneBuilder() {
        this.filter = new DocumentParameter();
        this.replacement = new DocumentParameter();
        this.options = new DocumentParameter();
    }

    @Override
    public ReplaceOneQuery build() {
        return new ReplaceOneQuery(
                lineNumber, columnNumber,
                comment, query, type,
                operation,
                parameters, modifiers,
                collection,
                filter, replacement, options
        );
    }

    @Override
    public void visitDocumentParameter(DocumentParameter parameter) {
        switch (parameters.size()) {
            case 0 -> filter = parameter;
            case 1 -> replacement = parameter;
            case 2 -> options = parameter;
            default -> throw new IllegalArgumentException();
        }
    }
}
