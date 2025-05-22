package cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.builder;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.enums.MongoCommands;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.exceptions.IncorrectParameterSyntax;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.modifier.QueryModifier;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.parameter.*;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.parameter.visitor.QueryParameterVisitor;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type.MongoQuery;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * Builder class for the creation of the MongoDB Query object.
 * Creates generic objects, i.e. undefined MongoDB commands in the system
 */
public class MongoQueryBuilder implements QueryParameterVisitor {
    protected int line;
    protected int column;
    protected String precedingComment;
    protected String query;
    protected MongoCommands type;
    @Getter
    protected String command;
    protected String collection;
    protected final List<QueryParameter> parameters;
    protected final List<QueryModifier> modifiers;
    protected final List<String> innerComments;

    public MongoQueryBuilder() {
        line = -1;
        column = -1;
        precedingComment = "";
        query = "";
        type = MongoCommands.UNRECOGNIZED;
        command = "";
        collection = "";
        parameters = new ArrayList<>();
        modifiers = new ArrayList<>();
        innerComments = new ArrayList<>();
    }

    public MongoQueryBuilder setPosition(int line, int column) {
        this.line = line;
        this.column = column;
        return this;
    }

    public MongoQueryBuilder setPrecedingComment(String precedingComment) {
        this.precedingComment = precedingComment;
        return this;
    }

    public MongoQueryBuilder appendInnerComment(String innerComment) {
        innerComments.add(innerComment);
        return this;
    }

    public MongoQueryBuilder setQuery(String query) {
        this.query = query;
        return this;
    }

    public MongoQueryBuilder setType(MongoCommands type) {
        this.type = type;
        return this;
    }

    public MongoQueryBuilder setCommand(String command) {
        this.command = command;
        return this;
    }

    public MongoQueryBuilder setCollection(String collection) {
        this.collection = collection;
        return this;
    }

    public MongoQueryBuilder addParameter(QueryParameter parameter) {
        if (!type.equals(MongoCommands.UNRECOGNIZED)) {
            parameter.accept(this);
        }
        this.parameters.add(parameter);
        return this;
    }

    public MongoQueryBuilder addModifier(QueryModifier modifier) {
        this.modifiers.add(modifier);
        return this;
    }

    public MongoQuery build() {
        return new MongoQuery(line, column, precedingComment, query, type, command, collection, parameters, modifiers, innerComments);
    }

    public int getParameterCount() {
        return parameters.size();
    }

    // Defines exception throwing for syntactically incorrectly used parameters
    @Override
    public void visitDocumentParameter(DocumentParameter parameter) {
        throw new IncorrectParameterSyntax("Document", parameters.size() + 1, command);
    }

    @Override
    public void visitStringParameter(StringParameter parameter) {
        throw new IncorrectParameterSyntax("String", parameters.size() + 1, command);
    }

    @Override
    public void visitArrayParameter(ArrayParameter parameter) {
        throw new IncorrectParameterSyntax("Array", parameters.size() + 1, command);
    }

    @Override
    public void visitEmptyParameter(EmptyParameter parameter) {
        throw new IncorrectParameterSyntax("Empty", parameters.size() + 1, command);
    }

    @Override
    public void visitFunctionParameter(FunctionParameter parameter) {
        throw new IncorrectParameterSyntax("Function", parameters.size() + 1, command);
    }
}
