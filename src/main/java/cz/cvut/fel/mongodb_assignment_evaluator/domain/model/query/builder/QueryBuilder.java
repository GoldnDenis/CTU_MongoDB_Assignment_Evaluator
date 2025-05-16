package cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.builder;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.enums.Operators;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.exceptions.IncorrectParameterSyntax;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.modifier.QueryModifier;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.parameter.*;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.parameter.visitor.QueryParameterVisitor;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type.QueryToken;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class QueryBuilder implements QueryParameterVisitor {
    protected int line;
    protected int column;
    protected String precedingComment;
    protected String query;
    protected Operators type;
    @Getter
    protected String operator;
    protected String collection;
    protected final List<QueryParameter> parameters;
    protected final List<QueryModifier> modifiers;
    protected final List<String> innerComments;

    public QueryBuilder() {
        line = -1;
        column = -1;
        precedingComment = "";
        query = "";
        type = Operators.UNRECOGNIZED;
        operator = "";
        collection = "";
        parameters = new ArrayList<>();
        modifiers = new ArrayList<>();
        innerComments = new ArrayList<>();
    }

    public QueryBuilder setPosition(int line, int column) {
        this.line = line;
        this.column = column;
        return this;
    }

    public QueryBuilder setPrecedingComment(String precedingComment) {
        this.precedingComment = precedingComment;
        return this;
    }

    public QueryBuilder appendInnerComment(String innerComment) {
        innerComments.add(innerComment);
        return this;
    }

    public QueryBuilder setQuery(String query) {
        this.query = query;
        return this;
    }

    public QueryBuilder setType(Operators type) {
        this.type = type;
        return this;
    }

    public QueryBuilder setOperator(String operator) {
        this.operator = operator;
        return this;
    }

    public QueryBuilder setCollection(String collection) {
        this.collection = collection;
        return this;
    }

    public QueryBuilder addParameter(QueryParameter parameter) {
        if (!type.equals(Operators.UNRECOGNIZED)) {
            parameter.accept(this);
        }
        this.parameters.add(parameter);
        return this;
    }

    public QueryBuilder addModifier(QueryModifier modifier) {
        this.modifiers.add(modifier);
        return this;
    }

    public QueryToken build() {
        return new QueryToken(line, column, precedingComment, query, type, operator, collection, parameters, modifiers, innerComments);
    }

    public int getParameterCount() {
        return parameters.size();
    }

    @Override
    public void visitDocumentParameter(DocumentParameter parameter) {
        throw new IncorrectParameterSyntax("Document", parameters.size() + 1, operator);
    }

    @Override
    public void visitStringParameter(StringParameter parameter) {
        throw new IncorrectParameterSyntax("String", parameters.size() + 1, operator);
    }

    @Override
    public void visitPipelineParameter(PipelineParameter parameter) {
        throw new IncorrectParameterSyntax("Pipeline", parameters.size() + 1, operator);
    }

    @Override
    public void visitEmptyParameter(EmptyParameter parameter) {
        throw new IncorrectParameterSyntax("Empty", parameters.size() + 1, operator);
    }

    @Override
    public void visitFunctionParameter(FunctionParameter parameter) {
        throw new IncorrectParameterSyntax("Function", parameters.size() + 1, operator);
    }
}
