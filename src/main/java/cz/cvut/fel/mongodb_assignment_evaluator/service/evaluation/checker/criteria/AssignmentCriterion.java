package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria;

import cz.cvut.fel.mongodb_assignment_evaluator.service.enums.CriterionStates;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.MockMongoDB;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.visitor.QueryParameterVisitor;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.*;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.query.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class AssignmentCriterion implements QueryParameterVisitor {
    protected final String assignmentMessage;
    protected int requiredCount;

    protected int currentCount;
    protected CriterionStates state;

    protected final MockMongoDB mockDb;
    protected int currentParameterIdx;

    protected final List<Query> satisfiedQueries;
    protected final List<Query> failedQueries;
    protected boolean satisfied;


    public AssignmentCriterion(MockMongoDB mockDb, String assignmentMessage, int requiredCount) {
        this.assignmentMessage = assignmentMessage;
        this.requiredCount = requiredCount;

        this.currentCount = 0;
        this.state = CriterionStates.NOT_FULFILLED;

        this.mockDb = mockDb;
        this.currentParameterIdx = -1;

        this.satisfiedQueries = new ArrayList<>();
        this.failedQueries = new ArrayList<>();
        this.satisfied = false;
    }

    public String generateFeedback() {
        evaluate();
        StringBuilder feedbackBuilder = new StringBuilder();
        feedbackBuilder.append('"').append(assignmentMessage).append('"')
                .append(" - ").append(currentCount).append(" out of ").append(requiredCount)
                .append(": ").append(state.getState()).append('\n');

        if (state.equals(CriterionStates.FULFILLED) || state.equals(CriterionStates.PARTLY_FULFILLED)) {
            feedbackBuilder.append(generateQueryRelatedFeedback());
        }

        return feedbackBuilder.toString();
    }

    protected void evaluate() {
        if (currentCount > 0 ) {
            state = CriterionStates.FULFILLED;
            if (currentCount < requiredCount) {
                state = CriterionStates.PARTLY_FULFILLED;
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
        concreteCheck(query);
        if (satisfied) {
            satisfiedQueries.add(query);
        } else {
            failedQueries.add(query);
        }
        satisfied = false;
    }

    protected abstract void concreteCheck(Query query);

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
