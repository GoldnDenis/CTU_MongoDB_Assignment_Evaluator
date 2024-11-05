package cz.cvut.fel.task_evaluator.evaluation.checker.criteria;


import cz.cvut.fel.task_evaluator.evaluation.checker.visitor.QueryParameterVisitor;
import cz.cvut.fel.task_evaluator.model.query.Query;
import cz.cvut.fel.task_evaluator.model.query.parameter.DocumentParameter;
import cz.cvut.fel.task_evaluator.model.query.parameter.EmptyParameter;
import cz.cvut.fel.task_evaluator.model.query.parameter.PipelineParameter;
import cz.cvut.fel.task_evaluator.model.query.parameter.StringParameter;


public abstract class Criterion implements QueryParameterVisitor {
    protected final String assignmentMessage;
//    private boolean fulfilled;


    public Criterion(String assignmentMessage) {
        this.assignmentMessage = assignmentMessage;
//        this.fulfilled = false;
    }

    public abstract void check(Query query);
    public abstract void generateFeedback();
    protected abstract boolean isFulfilled();

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
    public void visitEmptyParameter(EmptyParameter parameter) {
        // todo :: standard implementation
    }
}
