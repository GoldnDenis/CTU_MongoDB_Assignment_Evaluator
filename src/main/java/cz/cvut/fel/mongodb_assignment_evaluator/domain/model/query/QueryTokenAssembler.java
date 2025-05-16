package cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.enums.Operators;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.builder.QueryBuilder;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.builder.factory.QueryBuilderFactory;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.modifier.ModifierBuilder;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.parameter.QueryParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type.QueryToken;
import lombok.Setter;

public class QueryTokenAssembler {
    private final StringBuilder commentAccumulator;
    private final StringBuilder rawQueryAccumulator;
    @Setter
    private String lastQueryOperator;
    private QueryBuilder queryBuilder;
    private ModifierBuilder modifierBuilder;
    private int line;
    private int column;
    @Setter
    protected String collection;

    public QueryTokenAssembler() {
        commentAccumulator = new StringBuilder();
        rawQueryAccumulator = new StringBuilder();
        lastQueryOperator = "";
        queryBuilder = new QueryBuilder();
        modifierBuilder = new ModifierBuilder();
        collection = "";
    }

    public void appendComment(String comment) {
        if (queryBuilder.getOperator().isBlank()) {
            if (!lastQueryOperator.isEmpty()) {
                commentAccumulator.setLength(0);
            }
            if (!commentAccumulator.isEmpty()) {
                commentAccumulator.append("\n");
            }
            commentAccumulator.append(comment);
        } else {
            queryBuilder.appendInnerComment(comment);
        }
    }

    public void appendRawQuery(String string) {
        rawQueryAccumulator.append(string);
    }

    public void addParameter(QueryParameter parameter, Boolean isModifier) {
        if (isModifier) {
            modifierBuilder.setParameter(parameter);
        } else {
            queryBuilder.addParameter(parameter);
        }
    }

    public String getRawQuery() {
        return rawQueryAccumulator.toString();
    }

    public void addModifier() {
        queryBuilder.addModifier(modifierBuilder.build());
        modifierBuilder = new ModifierBuilder();
    }

    public void setCurrentPosition(int line, int column) {
        this.line = line;
        this.column = column;
    }

    public void setOperator(String operator, Boolean isModifier) {
        if (!isModifier) {
            if (!lastQueryOperator.isBlank() && !lastQueryOperator.equalsIgnoreCase(operator)) {
                commentAccumulator.setLength(0);
            }
            lastQueryOperator = operator;
            Operators type = Operators.fromString(operator);
            queryBuilder = QueryBuilderFactory.create(type);
            queryBuilder.setPosition(line, column)
                    .setCollection(collection)
                    .setOperator(operator)
                    .setType(type);
        } else {
            modifierBuilder.setModifier(operator);
        }
    }

    public void resetAccumulators() {
        queryBuilder = new QueryBuilder();
        modifierBuilder = new ModifierBuilder();
        rawQueryAccumulator.setLength(0);
        collection = "";
    }

    public QueryToken createQuery() {
        QueryToken query = queryBuilder.setPrecedingComment(commentAccumulator.toString())
                .setQuery(rawQueryAccumulator.toString())
                .build();
        resetAccumulators();
        return query;
    }

    public int getParameterCount() {
        return queryBuilder.getParameterCount();
    }
}
