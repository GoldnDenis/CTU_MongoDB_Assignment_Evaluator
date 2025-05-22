package cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.enums.MongoCommands;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.builder.MongoQueryBuilder;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.builder.factory.QueryBuilderFactory;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.modifier.ModifierBuilder;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.parameter.QueryParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type.MongoQuery;
import lombok.Setter;

/**
 * Accumulates all information about the query.
 * Orchestrates builders and accumulators to create MongoDB queries, including modifiers and comments.
 */
public class QueryAssembler {
    private final StringBuilder commentAccumulator;
    private final StringBuilder rawQueryAccumulator;
    @Setter
    private String lastQueryOperator;
    private MongoQueryBuilder mongoQueryBuilder;
    private ModifierBuilder modifierBuilder;
    private int line;
    private int column;
    @Setter
    protected String collection;

    public QueryAssembler() {
        commentAccumulator = new StringBuilder();
        rawQueryAccumulator = new StringBuilder();
        lastQueryOperator = "";
        mongoQueryBuilder = new MongoQueryBuilder();
        modifierBuilder = new ModifierBuilder();
        collection = "";
    }

    /**
     * After creation all accumulators are reset
     * @return
     */
    public MongoQuery createQuery() {
        MongoQuery mongoQuery = mongoQueryBuilder.setPrecedingComment(commentAccumulator.toString())
                .setQuery(rawQueryAccumulator.toString())
                .build();
        resetAccumulators();
        return mongoQuery;
    }

    public void appendComment(String comment) {
        if (mongoQueryBuilder.getCommand().isBlank()) {
            if (!lastQueryOperator.isEmpty()) {
                commentAccumulator.setLength(0);
            }
            if (!commentAccumulator.isEmpty()) {
                commentAccumulator.append("\n");
            }
            commentAccumulator.append(comment);
        } else {
            mongoQueryBuilder.appendInnerComment(comment);
        }
    }

    public void appendRawQuery(String string) {
        rawQueryAccumulator.append(string);
    }

    public void addParameter(QueryParameter parameter, Boolean isModifier) {
        if (isModifier) {
            modifierBuilder.setParameter(parameter);
        } else {
            mongoQueryBuilder.addParameter(parameter);
        }
    }

    public String getRawQuery() {
        return rawQueryAccumulator.toString();
    }

    public void addModifier() {
        mongoQueryBuilder.addModifier(modifierBuilder.build());
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
            MongoCommands type = MongoCommands.fromString(operator);
            mongoQueryBuilder = QueryBuilderFactory.create(type);
            mongoQueryBuilder.setPosition(line, column)
                    .setCollection(collection)
                    .setCommand(operator)
                    .setType(type);
        } else {
            modifierBuilder.setModifier(operator);
        }
    }

    public void resetAccumulators() {
        mongoQueryBuilder = new MongoQueryBuilder();
        modifierBuilder = new ModifierBuilder();
        rawQueryAccumulator.setLength(0);
        collection = "";
    }

    public int getParameterCount() {
        return mongoQueryBuilder.getParameterCount();
    }
}
