package cz.cvut.fel.task_evaluator.model.query;

import cz.cvut.fel.task_evaluator.enums.QueryTypes;
import cz.cvut.fel.task_evaluator.model.query.modifier.QueryModifier;
import cz.cvut.fel.task_evaluator.model.query.parameter.QueryParameter;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public final class Query {
    private final int lineNumber;
    private final int columnNumber;
    private final QueryTypes type;
    private final String comment;
    private final String operation;
    private final String collection;
    private final List<QueryParameter> parameters;
    private final List<QueryModifier> modifiers;

    public Query(int lineNumber, int columnNumber, String comment, String operation, String collection,
                 List<QueryParameter> parameters, List<QueryModifier> modifiers) {
        this.lineNumber = lineNumber;
        this.columnNumber = columnNumber;
        this.type = QueryTypes.fromString(operation);
        this.comment = comment;
        this.operation = operation;
        this.collection = collection;
        this.parameters = new ArrayList<>(parameters);
        this.modifiers = new ArrayList<>(modifiers);
    }

    @Override
    public String toString() {
        String query = !collection.isBlank() ? collection + "." + operation : operation;
        return "Query{" +
                "query='" + query + '\'' +
                " at " + lineNumber +
                ":" + columnNumber +
                '}';
    }
}
