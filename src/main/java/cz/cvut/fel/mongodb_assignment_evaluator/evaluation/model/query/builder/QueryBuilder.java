package cz.cvut.fel.mongodb_assignment_evaluator.evaluation.model.query.builder;

import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.model.parameter.*;
import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.model.enums.QueryTypes;
import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.model.parameter.visitor.QueryParameterVisitor;
import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.model.modifier.QueryModifier;
import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.model.query.type.Query;

import java.util.ArrayList;
import java.util.List;

public abstract class QueryBuilder implements QueryParameterVisitor {
    protected int lineNumber;
    protected int columnNumber;
    protected String comment;
    protected String query;
    protected QueryTypes type;
    protected String operation;
    protected String collection;
    protected final List<QueryParameter> parameters;
    protected final List<QueryModifier> modifiers;

    public QueryBuilder() {
        this.lineNumber = -1;
        this.columnNumber = -1;
        this.comment = "";
        this.query = "";
        this.type = QueryTypes.UNKNOWN;
        this.operation = "";
        this.collection = "";
        this.parameters = new ArrayList<>();
        this.modifiers = new ArrayList<>();
    }

    public QueryBuilder setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
        return this;
    }

    public QueryBuilder setColumnNumber(int columnNumber) {
        this.columnNumber = columnNumber;
        return this;
    }

    public QueryBuilder setComment(String comment) {
        this.comment = comment;
        return this;
    }

    public QueryBuilder setQuery(String query) {
        this.query = query;
        return this;
    }

    public QueryBuilder setType(QueryTypes type) {
        this.type = type;
        return this;
    }

    public QueryBuilder setOperation(String operation) {
        this.operation = operation;
        return this;
    }

    public QueryBuilder setCollection(String collection) {
        this.collection = collection;
        return this;
    }

    public QueryBuilder addParameter(QueryParameter parameter) {
        parameter.accept(this);
        this.parameters.add(parameter);
        return this;
    }

    public QueryBuilder addModifier(QueryModifier modifier) {
        this.modifiers.add(modifier);
        return this;
    }

    public abstract Query build();

    @Override
    public void visitDocumentParameter(DocumentParameter parameter) {
        throw new IllegalArgumentException("Wasn't expecting a document parameter");
    }

    @Override
    public void visitStringParameter(StringParameter parameter) {
        throw new IllegalArgumentException("Wasn't expecting a string parameter");
    }

    @Override
    public void visitPipelineParameter(PipelineParameter parameter) {
        throw new IllegalArgumentException("Wasn't expecting a pipeline parameter");
    }

    @Override
    public void visitEmptyParameter(EmptyParameter parameter) {
        throw new IllegalArgumentException("Wasn't expecting a empty parameter");
    }

    @Override
    public void visitFunctionParameter(FunctionParameter parameter) {
        throw new IllegalArgumentException("Wasn't expecting a function parameter");
    }
}
