package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria;

import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.MockMongoDB;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.visitor.QueryParameterVisitor;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.*;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.query.Query;

import java.util.ArrayList;
import java.util.List;

public abstract class AssignmentCriterion implements QueryParameterVisitor {
//public abstract class AssignmentCriterion {
    protected final String assignmentMessage;

    protected int requiredCount;
    protected int currentCount;

    protected final MockMongoDB mockDb;

    protected final List<Query> satisfiedQueries;
    protected final List<Query> failedQueries;
    protected boolean satisfied;


    public AssignmentCriterion(MockMongoDB mockDb, String assignmentMessage, int requiredCount) {
        this.mockDb = mockDb;

        this.assignmentMessage = assignmentMessage;

        this.requiredCount = requiredCount;
        this.currentCount = 0;

        this.satisfiedQueries = new ArrayList<>();
        this.failedQueries = new ArrayList<>();
        this.satisfied = false;
    }

    public void generateFeedback() {
        System.out.println("\n=======================================");
        System.out.println('"' + assignmentMessage + "\":");

        if (isFulfilled()) {
            for (int i = 1; i <= satisfiedQueries.size(); i++) {
                System.out.println(i + ") " + satisfiedQueries.get(i - 1));
            }
        } else {
            System.out.println("Not fulfilled");
        }

//        System.out.println("\tFailed:");
//        for (int i = 1; i <= failedQueries.size(); i++) {
//            System.out.println("\t\t" + i + ") " + failedQueries.get(i - 1));
//        }

//        if (isFulfilled()) {
//            generatePositiveFeedback();
//        } else {
//            generateNegativeFeedback();
//        }
    }

    protected boolean isFulfilled() {
        System.out.println(currentCount + "/" + requiredCount);
        return currentCount >= requiredCount;
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
//    protected abstract void generatePositiveFeedback();
//    protected abstract void generateNegativeFeedback();

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
