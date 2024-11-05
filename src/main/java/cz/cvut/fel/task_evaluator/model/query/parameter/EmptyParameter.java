package cz.cvut.fel.task_evaluator.model.query.parameter;

import cz.cvut.fel.task_evaluator.evaluation.checker.visitor.QueryParameterVisitor;

public class EmptyParameter implements QueryParameter {
    @Override
    public void accept(QueryParameterVisitor visitor) {
        visitor.visitEmptyParameter(this);
    }
}
