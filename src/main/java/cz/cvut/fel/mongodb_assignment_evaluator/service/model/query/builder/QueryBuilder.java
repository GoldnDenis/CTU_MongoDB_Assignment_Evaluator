package cz.cvut.fel.mongodb_assignment_evaluator.service.model.query.builder;

import cz.cvut.fel.mongodb_assignment_evaluator.service.enums.QueryTypes;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.visitor.QueryParameterVisitor;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.modifier.QueryModifier;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.*;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.query.type.Query;

import java.util.ArrayList;
import java.util.List;

public class QueryBuilder implements QueryParameterVisitor {
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
//        reset();
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

//    public QueryBuilder addParameter(QueryParameter parameter) {
//        this.parameters.add(parameter);
//        return this;
//    }

    public QueryBuilder addParameter(QueryParameter parameter) {
        parameter.accept(this);
        this.parameters.add(parameter);
        return this;
    }

    public QueryBuilder addModifier(QueryModifier modifier) {
        this.modifiers.add(modifier);
        return this;
    }

    public Query build() {
        return new Query(lineNumber, columnNumber, comment, query, type, operation, collection, parameters, modifiers);
    }

    @Override
    public void visitDocumentParameter(DocumentParameter parameter) {
        throw
    }

    @Override
    public void visitStringParameter(StringParameter parameter) {
        throw
    }

    @Override
    public void visitPipelineParameter(PipelineParameter parameter) {
        throw
    }

    @Override
    public void visitEmptyParameter(EmptyParameter parameter) {
        throw
    }

    @Override
    public void visitFunctionParameter(FunctionParameter parameter) {
        throw
    }

//    public void reset() {
//        this.lineNumber = -1;
//        this.columnNumber = -1;
//        this.comment = "";
//        this.query = "";
//        this.type = QueryTypes.UNKNOWN;
//        this.operation = "";
//        this.collection = "";
//        this.parameters.clear();
//        this.modifiers.clear();
//    }
}
