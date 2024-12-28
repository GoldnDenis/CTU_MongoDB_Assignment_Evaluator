package cz.cvut.fel.mongodb_assignment_evaluator.service.model.query.builder;

import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.*;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.query.type.Query;

public class UnknownBuilder extends QueryBuilder {
//    @Override
//    public UnknownQuery build() {
//        return new UnknownQuery(
//                lineNumber, columnNumber,
//                comment, query, type,
//                operation,
//                parameters, modifiers,
//                collection
//        );
//    }

    public Query build() {
        return new Query(lineNumber, columnNumber, comment, query, type, operation, collection, parameters, modifiers);
    }

    @Override
    public void visitDocumentParameter(DocumentParameter parameter) {
    }

    @Override
    public void visitStringParameter(StringParameter parameter) {
    }

    @Override
    public void visitPipelineParameter(PipelineParameter parameter) {
    }

    @Override
    public void visitEmptyParameter(EmptyParameter parameter) {
    }

    @Override
    public void visitFunctionParameter(FunctionParameter parameter) {
    }
}
