package cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.enums.Operators;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.entity.Criterion;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.modifier.QueryModifier;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.parameter.QueryParameter;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class QueryToken {
    protected final int line;
    protected final int column;
    protected final String query;
    protected final Operators type;
    protected final String precedingComment;
    protected final String operator;
    protected final String collection;
    protected final List<QueryParameter> parameters;
    protected final List<QueryModifier> modifiers;
    protected final List<String> innerComments;

    protected StringBuilder executionLogAccumulator;
    protected List<Criterion> fulfilledCriteria;

    public QueryToken(int line, int column, String precedingComment, String query,
                      Operators type, String operator, String collection,
                      List<QueryParameter> parameters, List<QueryModifier> modifiers, List<String> innerComments) {
        this.line = line;
        this.column = column;
        this.query = query;
        this.type = type;
        this.precedingComment = precedingComment;
        this.operator = operator;
        this.collection = collection;
        this.parameters = new ArrayList<>(parameters);
        this.modifiers = new ArrayList<>(modifiers);
        this.innerComments = new ArrayList<>(innerComments);

        this.executionLogAccumulator = new StringBuilder();
        this.fulfilledCriteria = new ArrayList<>();
    }

    public boolean operatorEquals(String word) {
        return operator != null && operator.equalsIgnoreCase(word);
    }

    public boolean isTrivial() {
        return parameters.isEmpty();
    }

    public int getQueryResultCount() {
        return isTrivial() ? 0 : 1;
    }

    public boolean isCommented() {
        return !precedingComment.isEmpty() || !innerComments.isEmpty();
    }

    public void addExecutionLog(String log) {
        executionLogAccumulator.append(log);
        if (!log.endsWith(System.lineSeparator()) || !log.endsWith("\n")) {
            executionLogAccumulator.append(System.lineSeparator());
        }
    }

    public String getExecutionLog() {
        return !executionLogAccumulator.isEmpty() ? executionLogAccumulator.toString() : "";
    }

    public void addCriterion(Criterion criterion) {
        fulfilledCriteria.add(criterion);
    }

    @Override
    public String toString() {
        return "Query at " +
                line + ":" +
                column + '\n' +
                query;
    }
}
