package cz.cvut.fel.mongodb_assignment_evaluator.application.model.query.type;

import cz.cvut.fel.mongodb_assignment_evaluator.enums.QueryTypes;
import cz.cvut.fel.mongodb_assignment_evaluator.application.model.modifier.QueryModifier;
import cz.cvut.fel.mongodb_assignment_evaluator.application.model.parameter.QueryParameter;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Query {
    protected final int line;
    protected final int column;
    protected final String query;
    protected final QueryTypes type;
    protected final String comment;
    protected final String operator;
    protected final String collection;
    protected final List<QueryParameter> parameters;
    protected final List<QueryModifier> modifiers;

    public Query(int line, int column, String comment, String query, QueryTypes type, String operator,
                 String collection, List<QueryParameter> parameters, List<QueryModifier> modifiers) {
        this.line = line;
        this.column = column;
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
                line + ":" +
                column + '\n' +
                comment + '\n' +
                query;
    }
}
