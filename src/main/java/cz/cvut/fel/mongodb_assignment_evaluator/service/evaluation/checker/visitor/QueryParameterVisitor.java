package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.visitor;

import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.*;

public interface QueryParameterVisitor {
    void visitDocumentParameter(DocumentParameter parameter);
    void visitStringParameter(StringParameter parameter);
    void visitPipelineParameter(PipelineParameter parameter);
    void visitStringLiteralParameter(FunctionParameter parameter);
    void visitEmptyParameter(EmptyParameter parameter);
}
