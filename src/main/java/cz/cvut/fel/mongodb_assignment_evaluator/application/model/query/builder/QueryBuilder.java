package cz.cvut.fel.mongodb_assignment_evaluator.application.model.query.builder;

import cz.cvut.fel.mongodb_assignment_evaluator.application.model.parameter.*;
import cz.cvut.fel.mongodb_assignment_evaluator.enums.QueryTypes;
import cz.cvut.fel.mongodb_assignment_evaluator.application.model.parameter.visitor.QueryParameterVisitor;
import cz.cvut.fel.mongodb_assignment_evaluator.application.model.modifier.QueryModifier;
import cz.cvut.fel.mongodb_assignment_evaluator.application.model.query.type.Query;

import java.util.ArrayList;
import java.util.List;

public class QueryBuilder implements QueryParameterVisitor {
    protected int line;
    protected int column;
    protected String comment;
    protected String query;
    protected QueryTypes type;
    protected String operation;
    protected String collection;
    protected final List<QueryParameter> parameters;
    protected final List<QueryModifier> modifiers;

    public QueryBuilder() {
        this.line = -1;
        this.column = -1;
        this.comment = "";
        this.query = "";
        this.type = QueryTypes.UNKNOWN;
        this.operation = "";
        this.collection = "";
        this.parameters = new ArrayList<>();
        this.modifiers = new ArrayList<>();
    }

    public QueryBuilder setPosition(int line, int column) {
        this.line = line;
        this.column = column;
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

    public QueryBuilder setOperator(String operator) {
        this.operation = operator;
        return this;
    }

    public QueryBuilder setCollection(String collection) {
        this.collection = collection;
        return this;
    }

    public QueryBuilder addParameter(QueryParameter parameter) {
        if (type != QueryTypes.UNKNOWN) {
            parameter.accept(this);
        }
        this.parameters.add(parameter);
        return this;
    }

    public QueryBuilder addModifier(QueryModifier modifier) {
        this.modifiers.add(modifier);
        return this;
    }

    public Query build() {
        return new Query(line, column, comment, query, type, operation, collection, parameters, modifiers);
    }

    public int getParameterCount() {
        return parameters.size();
    }

    @Override
    public void visitDocumentParameter(DocumentParameter parameter) {
        throw new IllegalArgumentException("Wasn't expecting an document parameter");
    }

    @Override
    public void visitStringParameter(StringParameter parameter) {
        throw new IllegalArgumentException("Wasn't expecting an string parameter");
    }

    @Override
    public void visitPipelineParameter(PipelineParameter parameter) {
        throw new IllegalArgumentException("Wasn't expecting an pipeline parameter");
    }

    @Override
    public void visitEmptyParameter(EmptyParameter parameter) {
        throw new IllegalArgumentException("Wasn't expecting an empty parameter");
    }

    @Override
    public void visitFunctionParameter(FunctionParameter parameter) {
        throw new IllegalArgumentException("Wasn't expecting an function parameter");
    }
}
