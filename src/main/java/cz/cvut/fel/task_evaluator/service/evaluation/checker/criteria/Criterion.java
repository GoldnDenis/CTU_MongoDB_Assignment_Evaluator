package cz.cvut.fel.task_evaluator.service.evaluation.checker.criteria;


import cz.cvut.fel.task_evaluator.service.evaluation.checker.visitor.QueryParameterVisitor;
import cz.cvut.fel.task_evaluator.service.evaluation.query.Query;
import cz.cvut.fel.task_evaluator.evaluation.query.parameter.*;
import cz.cvut.fel.task_evaluator.service.evaluation.query.parameter.*;


public abstract class Criterion implements QueryParameterVisitor {
    private final String assignmentMessage;
//    private boolean fulfilled;


    public Criterion(String assignmentMessage) {
        this.assignmentMessage = assignmentMessage;
//        this.fulfilled = false;
    }

    public void generateFeedback() {
        System.out.println("\n=======================================");
        System.out.println(assignmentMessage + ":");
        if (isFulfilled()) {
            generatePositiveFeedback();
        } else {
            generateNegativeFeedback();
        }
    }

    public abstract void check(Query query);
    protected abstract boolean isFulfilled();
    protected abstract void generatePositiveFeedback();
    protected abstract void generateNegativeFeedback();

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
    public void visitStringLiteralParameter(StringLiteralParameter parameter) {
        // todo :: standard implementation
    }

    @Override
    public void visitEmptyParameter(EmptyParameter parameter) {
        // todo :: standard implementation
    }
}
