package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.parser.fsm;

import cz.cvut.fel.mongodb_assignment_evaluator.service.enums.QueryTypes;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.parser.fsm.state.ParserState;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.parser.fsm.state.ScriptState;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.parser.iterator.LineIterator;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.modifier.ModifierBuilder;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.QueryParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.query.factory.QueryBuilderFactory;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.query.type.Query;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.query.builder.QueryBuilder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class ParserStateMachine {
    @Setter
    private ParserState state;
    @Getter
    private final List<Query> queryList;

    @Getter
    private QueryBuilder queryBuilder;
    @Getter
    private ModifierBuilder modifierBuilder;

    private String lastQueryOperation;
    @Setter
    private String currentCollection;

    private final StringBuilder queryAccumulator;
    private final StringBuilder lastCommentBuilder;

    public ParserStateMachine() {
        this.state = new ScriptState(this);
        this.queryList = new ArrayList<>();

        this.queryBuilder = new QueryBuilder();
        this.modifierBuilder = new ModifierBuilder();

        this.lastQueryOperation = "";
        this.currentCollection = "";

        this.lastCommentBuilder = new StringBuilder();
        this.queryAccumulator = new StringBuilder();
//        this.lastComment = "";
    }

    public void parseLine(LineIterator line) {
        while (line.hasNext()) {
            state.process(line);
        }
    }

    public void appendToComment(String comment) {
        if (!lastQueryOperation.isEmpty()) {
            lastCommentBuilder.setLength(0);
        }
        if (!lastCommentBuilder.isEmpty()) {
            lastCommentBuilder.append("\n");
        }
        lastCommentBuilder.append(comment);
    }

    public void appendToQuery(String string) {
        queryAccumulator.append(string);
    }

    public void appendToQuery(Character character) {
        queryAccumulator.append(character);
    }

    public void setQueryOperator(String operator) {
        if (!lastQueryOperation.isBlank() && !lastQueryOperation.equals(operator)) {
            lastCommentBuilder.setLength(0);
//            lastComment = "";
        }
        lastQueryOperation = operator;

        QueryTypes type = QueryTypes.fromString(operator);
        queryBuilder = QueryBuilderFactory.createQueryBuilder(type);

        queryBuilder.setCollection(currentCollection);
        queryBuilder.setOperation(operator);
        queryBuilder.setType(type);
    }

    public void addParameter(QueryParameter parameter, Boolean isModifier) {
        if (isModifier) {
            modifierBuilder.setParameter(parameter);
        } else {
            queryBuilder.addParameter(parameter);
        }
    }

    public void resetAccumulators() {
        queryBuilder = new QueryBuilder();
        modifierBuilder = new ModifierBuilder();
        queryAccumulator.setLength(0);
        currentCollection = "";
    }

    public void saveModifier() {
        queryBuilder.addModifier(modifierBuilder.build());
        this.modifierBuilder = new ModifierBuilder();
    }

    public void saveQuery() {
        queryList.add(
                queryBuilder.setComment(lastCommentBuilder.toString())
                        .setQuery(queryAccumulator.toString())
                        .build()
        );
        resetAccumulators();
    }
}
