package cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.parameter.visitor;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.parameter.*;

/**
 * Visitor interface for unique processing of different parameter types
 */
public interface QueryParameterVisitor {
    void visitDocumentParameter(DocumentParameter parameter);

    void visitEmptyParameter(EmptyParameter parameter);

    void visitFunctionParameter(FunctionParameter parameter);

    void visitArrayParameter(ArrayParameter parameter);

    void visitStringParameter(StringParameter parameter);
}
