package cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter;

import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.visitor.QueryParameterVisitor;

public interface QueryParameter {
    void accept(QueryParameterVisitor visitor);
    boolean isTrivial();
}
