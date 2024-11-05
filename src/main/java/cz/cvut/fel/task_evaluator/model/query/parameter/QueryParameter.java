package cz.cvut.fel.task_evaluator.model.query.parameter;

import cz.cvut.fel.task_evaluator.evaluation.checker.visitor.QueryParameterVisitor;

public interface QueryParameter {
    void accept(QueryParameterVisitor visitor);
}
