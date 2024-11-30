package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.parser.fsm;

import cz.cvut.fel.mongodb_assignment_evaluator.service.enums.QueryTypes;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.parser.fsm.state.ParserState;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.parser.fsm.state.ScriptState;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.parser.iterator.LineIterator;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.modifier.ModifierBuilder;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.QueryParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.query.Query;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.query.QueryBuilder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class ParserStateMachine {
    @Setter
    private ParserState state;
    @Getter
    private final List<Query> queryList;

    private final QueryBuilder queryBuilder;
    private final ModifierBuilder modifierBuilder;
    private final StringBuilder queryAccumulator;

    @Setter
    private String lastQueryOperation;
    @Setter
    private StringBuilder lastCommentBuilder;

    public ParserStateMachine() {
        this.state = new ScriptState(this);
        this.queryList = new ArrayList<>();

        this.queryBuilder = new QueryBuilder();
        this.modifierBuilder = new ModifierBuilder();
        this.queryAccumulator = new StringBuilder();

        this.lastQueryOperation = "";
        this.lastCommentBuilder = new StringBuilder();
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

    public void setQueryPosition(int lineNumber, int columnNumber) {
        queryBuilder.setLineNumber(lineNumber)
                .setColumnNumber(columnNumber);
    }

    public void setQueryOperator(QueryTypes type) {
        queryBuilder.setType(type);
    }

    public void setQueryOperator(String operator) {
        if (!lastQueryOperation.isBlank() && !lastQueryOperation.equals(operator)) {
            lastCommentBuilder.setLength(0);
//            lastComment = "";
        }
        lastQueryOperation = operator;
        queryBuilder.setOperation(operator);
        queryBuilder.setType(QueryTypes.fromString(operator));
    }

    public void setQueryCollection(String collection) {
        queryBuilder.setCollection(collection);
    }

    public void resetQuery() {
        queryBuilder.reset();
        modifierBuilder.reset();
        queryAccumulator.setLength(0);
    }

    public void addParameter(QueryParameter parameter, Boolean isModifier) {
        if (isModifier) {
            modifierBuilder.setParameter(parameter);
        } else {
            queryBuilder.addParameter(parameter);
        }
    }

    public void setModifierOperator(String modifier) {
        modifierBuilder.setModifier(modifier);
    }

    public void resetAccumulators() {
        queryBuilder.reset();
        modifierBuilder.reset();
        queryAccumulator.setLength(0);
    }

    public void saveModifier() {
        queryBuilder.addModifier(modifierBuilder.build());
        modifierBuilder.reset();
    }

    public void saveQuery() {
        queryList.add(
                queryBuilder.setComment(lastCommentBuilder.toString())
                        .setQuery(queryAccumulator.toString())
                        .build()
        );
        queryBuilder.reset();
        queryAccumulator.setLength(0);
    }
}
