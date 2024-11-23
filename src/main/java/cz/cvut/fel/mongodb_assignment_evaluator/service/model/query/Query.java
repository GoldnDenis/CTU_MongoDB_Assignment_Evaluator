package cz.cvut.fel.mongodb_assignment_evaluator.service.model.query;

import cz.cvut.fel.mongodb_assignment_evaluator.service.enums.QueryTypes;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.modifier.QueryModifier;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.QueryParameter;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public final class Query {
    private final int lineNumber;
    private final int columnNumber;
    private final String query;
    private final QueryTypes type;
    private final String comment;
    private final String operator;
    private final String collection;
    private final List<QueryParameter> parameters;
    private final List<QueryModifier> modifiers;

    public Query(int lineNumber, int columnNumber, String comment, String query, QueryTypes type, String operator, String collection,
                 List<QueryParameter> parameters, List<QueryModifier> modifiers) {
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
