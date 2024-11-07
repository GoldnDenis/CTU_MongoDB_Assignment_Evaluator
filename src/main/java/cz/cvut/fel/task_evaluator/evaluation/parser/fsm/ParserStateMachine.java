package cz.cvut.fel.task_evaluator.evaluation.parser.fsm;

import cz.cvut.fel.task_evaluator.evaluation.parser.fsm.state.ParserState;
import cz.cvut.fel.task_evaluator.evaluation.parser.fsm.state.ParserTransitionState;
import cz.cvut.fel.task_evaluator.evaluation.parser.iterator.LineIterator;
import cz.cvut.fel.task_evaluator.model.query.Query;
import cz.cvut.fel.task_evaluator.model.query.QueryBuilder;
import cz.cvut.fel.task_evaluator.model.query.modifier.ModifierBuilder;
import cz.cvut.fel.task_evaluator.model.query.parameter.QueryParameter;
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

    @Setter
    private String lastQueryOperation;
    @Setter
    private String lastComment;

    public ParserStateMachine() {
        this.state = new ParserTransitionState(this);
        this.queryList = new ArrayList<>();
        this.queryBuilder = new QueryBuilder();
        this.modifierBuilder = new ModifierBuilder();
        this.lastQueryOperation = "";
        this.lastComment = "";
    }

    public void parseLine(LineIterator line) {
        while (line.hasNext()) {
            state.process(line);
        }
    }

    public void setQueryPosition(int lineNumber, int columnNumber) {
        queryBuilder.setLineNumber(lineNumber)
                .setColumnNumber(columnNumber);
    }

    public void setQueryOperator(String operator) {
        if (!lastQueryOperation.isBlank() && !lastQueryOperation.equals(operator)) {
            lastComment = "";
        }
        lastQueryOperation = operator;
        queryBuilder.setOperation(operator);
    }

    public void setQueryCollection(String collection) {
        queryBuilder.setCollection(collection);
    }

    public void resetQueryBuilder() {
        queryBuilder.reset();
    }

    public void saveQuery() {
        queryList.add(
                queryBuilder.setComment(lastComment)
                        .build()
        );
        queryBuilder.reset();
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

    public void saveModifier() {
        queryBuilder.addModifier(modifierBuilder.build());
        modifierBuilder.reset();
    }
}
