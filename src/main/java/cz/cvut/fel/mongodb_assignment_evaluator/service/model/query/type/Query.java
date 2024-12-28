package cz.cvut.fel.mongodb_assignment_evaluator.service.model.query.type;

import cz.cvut.fel.mongodb_assignment_evaluator.service.enums.QueryTypes;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.modifier.QueryModifier;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.QueryParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.query.type.visitor.QueryVisitor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Query {
    protected final int lineNumber;
    protected final int columnNumber;
    protected final String query;
    protected final QueryTypes type;
    protected final String comment;
    protected final String operator;
    protected final String collection;
    protected final List<QueryParameter> parameters;
    protected final List<QueryModifier> modifiers;

    public Query(int lineNumber, int columnNumber, String comment, String query, QueryTypes type, String operator,
                 String collection, List<QueryParameter> parameters, List<QueryModifier> modifiers) {
        this.lineNumber = lineNumber;
        this.columnNumber = columnNumber;
        this.query = query;
        this.type = type;
        this.comment = comment;
        this.operator = operator;
        this.collection = collection;
        this.parameters = new ArrayList<>(parameters);
        this.modifiers = new ArrayList<>(modifiers);
    }

    @Override
    public String toString() {
        return "Query at " +
                lineNumber + ":" +
                columnNumber + '\n' +
                comment + '\n' +
                query;
    }
}
