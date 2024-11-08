package cz.cvut.fel.task_evaluator.service.evaluation.query.parameter;

import cz.cvut.fel.task_evaluator.service.evaluation.checker.visitor.QueryParameterVisitor;

public interface QueryParameter {
    void accept(QueryParameterVisitor visitor);
}
