package cz.cvut.fel.task_evaluator.evaluation.checker.visitor;

import cz.cvut.fel.task_evaluator.model.query.parameter.DocumentParameter;
import cz.cvut.fel.task_evaluator.model.query.parameter.EmptyParameter;
import cz.cvut.fel.task_evaluator.model.query.parameter.PipelineParameter;
import cz.cvut.fel.task_evaluator.model.query.parameter.StringParameter;

public interface QueryParameterVisitor {
    void visitDocumentParameter(DocumentParameter parameter);
    void visitStringParameter(StringParameter parameter);
    void visitPipelineParameter(PipelineParameter parameter);
    void visitEmptyParameter(EmptyParameter parameter);
}
