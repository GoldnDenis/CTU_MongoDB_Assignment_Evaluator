package cz.cvut.fel.mongodb_assignment_evaluator.evaluation.model.parameter.visitor;

import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.model.parameter.*;

public interface QueryParameterVisitor {
    void visitDocumentParameter(DocumentParameter parameter);
    void visitEmptyParameter(EmptyParameter parameter);
    void visitFunctionParameter(FunctionParameter parameter);
    void visitPipelineParameter(PipelineParameter parameter);
    void visitStringParameter(StringParameter parameter);
}