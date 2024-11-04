package cz.cvut.fel.task_evaluator.model.query;

import cz.cvut.fel.task_evaluator.model.query.modifier.QueryModifier;
import cz.cvut.fel.task_evaluator.model.query.parameter.QueryParameter;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
public final class Query {
    private final int lineNumber;
    private final int columnNumber;
    private final String comment;
    private final String operation;
    private final String collection;
    private final List<QueryParameter> parameters;
    private final List<QueryModifier> modifiers;

    public Query(int lineNumber, int columnNumber, String comment, String operation, String collection,
                 List<QueryParameter> parameters, List<QueryModifier> modifiers) {
        this.lineNumber = lineNumber;
        this.columnNumber = columnNumber;
        this.comment = comment;
        this.operation = operation;
        this.collection = collection;
        this.parameters = new ArrayList<>(parameters);
        this.modifiers = new ArrayList<>(modifiers);
    }
}
