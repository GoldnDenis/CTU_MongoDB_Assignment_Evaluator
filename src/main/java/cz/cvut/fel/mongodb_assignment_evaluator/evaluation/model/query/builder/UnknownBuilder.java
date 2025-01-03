package cz.cvut.fel.mongodb_assignment_evaluator.evaluation.model.query.builder;

import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.model.parameter.*;
import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.model.query.type.Query;

public class UnknownBuilder extends QueryBuilder {
    public Query build() {
        return new Query(lineNumber, columnNumber, comment, query, type, operation, collection, parameters, modifiers);
    }

    @Override
    public QueryBuilder addParameter(QueryParameter parameter) {
        this.parameters.add(parameter);
        return this;
    }
}
