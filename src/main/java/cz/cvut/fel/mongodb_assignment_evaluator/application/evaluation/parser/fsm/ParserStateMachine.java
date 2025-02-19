package cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.parser.fsm;

import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.StudentEvaluator;
import cz.cvut.fel.mongodb_assignment_evaluator.enums.StudentErrorTypes;
import cz.cvut.fel.mongodb_assignment_evaluator.enums.QueryTypes;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.parser.fsm.state.ParserState;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.parser.fsm.state.ScriptState;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.parser.iterator.LineIterator;
import cz.cvut.fel.mongodb_assignment_evaluator.application.model.modifier.ModifierBuilder;
import cz.cvut.fel.mongodb_assignment_evaluator.application.model.parameter.QueryParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.application.model.query.factory.QueryBuilderFactory;
import cz.cvut.fel.mongodb_assignment_evaluator.application.model.query.type.Query;
import cz.cvut.fel.mongodb_assignment_evaluator.application.model.query.builder.QueryBuilder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class ParserStateMachine {
    @Setter
    private ParserState state;
    @Getter
    private final List<Query> queryList;

    @Getter
    private QueryBuilder queryBuilder;
    @Getter
    private ModifierBuilder modifierBuilder;
    @Setter
    private String lastQueryOperation;
    @Setter
    private String currentCollection;
    private int currentLine;
    private int currentRow;

    private final StringBuilder queryAccumulator;
    private final StringBuilder lastCommentBuilder;

    public ParserStateMachine() {
        this.state = new ScriptState(this);
        this.queryList = new ArrayList<>();

        this.queryBuilder = new QueryBuilder();
        this.modifierBuilder = new ModifierBuilder();

        this.lastQueryOperation = "";

        this.currentCollection = "";
        this.currentLine = -1;
        this.currentRow = -1;

        this.lastCommentBuilder = new StringBuilder();
        this.queryAccumulator = new StringBuilder();
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
        }
        lastQueryOperation = operator;

        QueryTypes type = QueryTypes.fromString(operator);
        queryBuilder = QueryBuilderFactory.create(type);

        queryBuilder.setPosition(currentLine, currentRow)
                .setCollection(currentCollection)
                .setOperator(operator)
                .setType(type);
    }

    public void addParameter(QueryParameter parameter, Boolean isModifier) {
        try {
            if (isModifier) {
                modifierBuilder.setParameter(parameter);
            } else {
                queryBuilder.addParameter(parameter);
            }
        } catch (IllegalArgumentException e) {
            StudentEvaluator.getErrorCollector().addLog(Level.WARNING, StudentErrorTypes.PARSER, e.getMessage());
            state = new ScriptState(this);
            resetAccumulators();
        }
    }

    public void resetAccumulators() {
        queryBuilder = new QueryBuilder();
        modifierBuilder = new ModifierBuilder();
        queryAccumulator.setLength(0);
        currentCollection = "";
        currentRow = -1;
        currentLine = -1;
    }

    public void setCurrentPosition(LineIterator iterator) {
        currentLine = iterator.getRowIndex() + 1;
        currentRow = iterator.getCurrentColumnIndex() + 1;
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
