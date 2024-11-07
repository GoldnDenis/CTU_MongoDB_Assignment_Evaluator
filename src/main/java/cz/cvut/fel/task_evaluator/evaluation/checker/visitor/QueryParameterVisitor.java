package cz.cvut.fel.task_evaluator.evaluation.checker.visitor;

import cz.cvut.fel.task_evaluator.model.query.parameter.*;

public interface QueryParameterVisitor {
    void visitDocumentParameter(DocumentParameter parameter);
    void visitStringParameter(StringParameter parameter);
    void visitPipelineParameter(PipelineParameter parameter);
    void visitStringLiteralParameter(StringLiteralParameter parameter);
    void visitEmptyParameter(EmptyParameter parameter);
}
