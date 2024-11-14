package cz.cvut.fel.mongodb_assignment_evaluator.service.model.query;

import cz.cvut.fel.mongodb_assignment_evaluator.service.enums.QueryTypes;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.modifier.QueryModifier;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.QueryParameter;

import java.util.ArrayList;
import java.util.List;

public class QueryBuilder {
    private int lineNumber;
    private int columnNumber;
    private String comment;

    private String query;
    private QueryTypes type;

    private String operation;
    private String collection;

    private final List<QueryParameter> parameters;
    private final List<QueryModifier> modifiers;

    public QueryBuilder() {
        this.parameters = new ArrayList<>();
        this.modifiers = new ArrayList<>();
        reset();
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

    public void reset() {
        this.lineNumber = -1;
        this.columnNumber = -1;
        this.comment = "";
        this.query = "";
        this.type = QueryTypes.UNKNOWN;
        this.operation = "";
        this.collection = "";
        this.parameters.clear();
        this.modifiers.clear();
    }
}
