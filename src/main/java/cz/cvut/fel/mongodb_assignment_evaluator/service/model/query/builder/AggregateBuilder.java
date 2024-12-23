package cz.cvut.fel.mongodb_assignment_evaluator.service.model.query.builder;

import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.*;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.visitor.QueryParameterVisitor;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.query.type.AggregateQuery;

import java.util.ArrayList;
import java.util.List;

public class AggregateBuilder extends QueryBuilder {
    private final List<DocumentParameter> aggregationPipeline;
    private DocumentParameter options;

    public AggregateBuilder() {
        this.aggregationPipeline = new ArrayList<>();
        this.options = new DocumentParameter();
    }

    @Override
    public AggregateQuery build() {
        return new AggregateQuery(
                lineNumber, columnNumber,
                comment, query, type,
                operation,
                parameters, modifiers,
                collection,
                aggregationPipeline, options
        );
    }

    @Override
    public void visitDocumentParameter(DocumentParameter parameter) {
        switch (parameters.size()) {
            case 1: options = parameter;
            default: throw new IllegalArgumentException();
        }
    }

//    @Override
//    public void visitStringParameter(StringParameter parameter) {
//        throw
//    }

    @Override
    public void visitPipelineParameter(PipelineParameter parameter) {
        switch (parameters.size()) {
            case 0: aggregationPipeline.addAll(parameter.getParameterList());
            default: throw new IllegalArgumentException();
        }
    }

//    @Override
//    public void visitStringLiteralParameter(FunctionParameter parameter) {
//        throw
//    }
//
//    @Override
//    public void visitEmptyParameter(EmptyParameter parameter) {
//        throw
//    }
//
//    @Override
//    public void visitFunctionParameter(FunctionParameter parameter) {
//        throw
//    }
}
