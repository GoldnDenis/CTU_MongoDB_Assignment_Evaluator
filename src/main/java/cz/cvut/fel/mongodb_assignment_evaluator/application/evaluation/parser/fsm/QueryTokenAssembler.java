package cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.parser.fsm;

import cz.cvut.fel.mongodb_assignment_evaluator.application.model.modifier.ModifierBuilder;
import cz.cvut.fel.mongodb_assignment_evaluator.application.model.parameter.QueryParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.application.model.query.builder.QueryBuilder;
import cz.cvut.fel.mongodb_assignment_evaluator.application.model.query.factory.QueryBuilderFactory;
import cz.cvut.fel.mongodb_assignment_evaluator.application.model.query.type.Query;
import cz.cvut.fel.mongodb_assignment_evaluator.enums.QueryTypes;
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
//    @Setter
//    protected String operator;
//    protected QueryTypes type;
//    protected final List<QueryParameter> parameters;

//    protected final List<QueryModifier> modifiers;

    public QueryTokenAssembler() {
        commentAccumulator = new StringBuilder();
        rawQueryAccumulator = new StringBuilder();
        lastQueryOperator = "";
        queryBuilder = new QueryBuilder();
        modifierBuilder = new ModifierBuilder();
//        line = 0;
//        column = 0;
        collection = "";
//        operator = "";
//        type = QueryTypes.UNKNOWN;
//        parameters = new ArrayList<>();
//        modifiers = new ArrayList<>();
    }

    public void appendComment(String comment) {
        if (!lastQueryOperator.isEmpty()) {
            commentAccumulator.setLength(0);
        }
        if (!commentAccumulator.isEmpty()) {
            commentAccumulator.append("\n");
        }
        commentAccumulator.append(comment);
    }

    public void appendRawQuery(String string) {
        rawQueryAccumulator.append(string);
    }

    public void appendRawQuery(Character c) {
        rawQueryAccumulator.append(c);
    }

    public void addParameter(QueryParameter parameter, Boolean isModifier) {
        if (isModifier) {
            modifierBuilder.setParameter(parameter);
        } else {
            queryBuilder.addParameter(parameter);
        }
    }

    public void setModifierOperator(String operator) {
        modifierBuilder.setModifier(operator);
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
            if (!lastQueryOperator.isBlank() && !lastQueryOperator.equals(operator)) {
                commentAccumulator.setLength(0);
            }
            lastQueryOperator = operator;

            QueryTypes type = QueryTypes.fromString(operator);
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
//        currentRow = -1;
//        currentLine = -1;
    }

    public Query createQuery() {
        Query query = queryBuilder.setComment(commentAccumulator.toString())
                .setQuery(rawQueryAccumulator.toString())
                .build();
        resetAccumulators();
        return query;
    }

    public int getParameterCount() {
        return queryBuilder.getParameterCount();
    }
}
