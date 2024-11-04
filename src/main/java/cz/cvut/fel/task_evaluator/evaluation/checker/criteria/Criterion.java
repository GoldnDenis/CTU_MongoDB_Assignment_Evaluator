package cz.cvut.fel.task_evaluator.evaluation.checker.criteria;


import cz.cvut.fel.task_evaluator.model.query.Query;


public abstract class Criterion {
    private final String assignmentMessage;
    private boolean fulfilled;


    public Criterion(String assignmentMessage) {
        this.assignmentMessage = assignmentMessage;
        this.fulfilled = false;
    }

    public abstract void check(Query query);

    public abstract boolean isFulfilled();
}
