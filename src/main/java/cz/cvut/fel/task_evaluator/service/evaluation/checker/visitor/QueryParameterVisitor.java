package cz.cvut.fel.task_evaluator.service.evaluation.checker.visitor;

import cz.cvut.fel.task_evaluator.service.evaluation.query.parameter.*;

public interface QueryParameterVisitor {
    void visitDocumentParameter(DocumentParameter parameter);
    void visitStringParameter(StringParameter parameter);
    void visitPipelineParameter(PipelineParameter parameter);
    void visitStringLiteralParameter(FunctionParameter parameter);
    void visitEmptyParameter(EmptyParameter parameter);
}