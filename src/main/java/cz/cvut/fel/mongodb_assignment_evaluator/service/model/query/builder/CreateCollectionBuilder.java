package cz.cvut.fel.mongodb_assignment_evaluator.service.model.query.builder;

import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.*;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.query.type.CreateCollectionQuery;


public class CreateCollectionBuilder extends QueryBuilder {
    private StringParameter collectionName;
    private DocumentParameter options;

    public CreateCollectionBuilder() {
        collectionName = new StringParameter();
        options = new DocumentParameter();
    }

    @Override
    public CreateCollectionQuery build() {
        return new CreateCollectionQuery(
                lineNumber, columnNumber,
                comment, query, type,
                operation, "",
                parameters, modifiers,
                collectionName, options
        );
    }

    @Override
    public void visitDocumentParameter(DocumentParameter parameter) {
        switch (parameters.size()) {
            case 1 -> options = parameter;
            default -> throw new IllegalArgumentException();
        }
    }

    @Override
    public void visitStringParameter(StringParameter parameter) {
        switch (parameters.size()) {
            case 0 -> collectionName = parameter;
            default -> throw new IllegalArgumentException();
        }
    }
}
