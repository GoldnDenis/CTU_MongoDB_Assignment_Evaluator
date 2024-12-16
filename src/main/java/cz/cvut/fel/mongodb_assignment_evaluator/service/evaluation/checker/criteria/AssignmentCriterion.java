package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria;

import cz.cvut.fel.mongodb_assignment_evaluator.service.enums.ResultStates;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.MockMongoDB;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.visitor.QueryParameterVisitor;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.*;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.query.Query;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class AssignmentCriterion implements QueryParameterVisitor {
    protected final MockMongoDB mockDb;

    protected final String assignmentMessage;
    protected int requiredCount;
//    protected final int minArgCount;

    protected int currentCount;
    protected final List<Query> satisfiedQueries;
    protected final List<Query> failedQueries;

    protected ResultStates state;

    protected int curParamIdx;

    protected boolean satisfied;

    @Getter
    protected EvaluationResult result;

    public AssignmentCriterion(MockMongoDB mockDb, String assignmentMessage, int requiredCount) {
        this.mockDb = mockDb;

        this.assignmentMessage = assignmentMessage;
        this.requiredCount = requiredCount;

        this.currentCount = 0;
        this.satisfiedQueries = new ArrayList<>();
        this.failedQueries = new ArrayList<>();

        this.state = ResultStates.NOT_FULFILLED;
        this.curParamIdx = -1;

//        this.satisfied = false;
    }

//    public AssignmentCriterion(MockMongoDB mockDb, String assignmentMessage, int requiredCount, int minArgCount) {
//        this.mockDb = mockDb;
//
//        this.assignmentMessage = assignmentMessage;
//        this.requiredCount = requiredCount;
//        this.minArgCount = minArgCount;
//
//        this.currentCount = 0;
//        this.satisfiedQueries = new ArrayList<>();
//        this.failedQueries = new ArrayList<>();
//
//        this.state = ResultStates.NOT_FULFILLED;
//        this.curParamIdx = -1;
//
////        this.satisfied = false;
//    }

    public String generateFeedback() {
        evaluate();
        StringBuilder feedbackBuilder = new StringBuilder();
        feedbackBuilder.append('"').append(assignmentMessage).append('"')
                .append(" - ").append(currentCount).append(" out of ").append(requiredCount)
                .append(": ").append(state.getState()).append('\n');

        if (state.equals(ResultStates.FULFILLED) || state.equals(ResultStates.PARTLY_FULFILLED)) {
            feedbackBuilder.append(generateQueryRelatedFeedback());
        }

        return feedbackBuilder.toString();
    }

    protected void evaluate() {
        if (currentCount > 0 ) {
            state = ResultStates.FULFILLED;
            if (currentCount < requiredCount) {
                state = ResultStates.PARTLY_FULFILLED;
            }
        }
    }

    protected String generateQueryRelatedFeedback() {
        return "Satisfied queries:\n" +
                satisfiedQueries.stream()
                        .map(query -> query.toString() + '\n')
                        .collect(Collectors.joining());
    }

    public void check(Query query) {
        int lastCount = currentCount;
        concreteCheck(query);
        log(query, lastCount != currentCount);

//        if (satisfied) {
//            satisfiedQueries.add(query);
//        } else {
//            failedQueries.add(query);
//        }
//        satisfied = false;
    }

    protected abstract void concreteCheck(Query query);

    protected void checkParameters(List<QueryParameter> parameters) {
        for (QueryParameter parameter : parameters) {
            curParamIdx++;
            if (curParamIdx == requiredCount) {
                break;
            }
            parameter.accept(this);
        }

    }

    public void log(Query query, Boolean satisfied) {
        result.logQuery(query, satisfied);
//        if (satisfied) {
//            satisfiedQueries.add(query);
//        } else {
//            failedQueries.add(query);
//        }
    }


    @Override
    public void visitDocumentParameter(DocumentParameter parameter) {
        // todo :: standard implementation
    }

    @Override
    public void visitStringParameter(StringParameter parameter) {
        // todo :: standard implementation
    }

    @Override
    public void visitPipelineParameter(PipelineParameter parameter) {
        // todo :: standard implementation
    }

    @Override
    public void visitStringLiteralParameter(FunctionParameter parameter) {
        // todo :: standard implementation
    }

    @Override
    public void visitEmptyParameter(EmptyParameter parameter) {
        // todo :: standard implementation
    }
}
