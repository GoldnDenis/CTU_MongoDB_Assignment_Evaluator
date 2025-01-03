package cz.cvut.fel.mongodb_assignment_evaluator.evaluation.model.parameter;

import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.model.parameter.visitor.QueryParameterVisitor;

public interface QueryParameter {
    void accept(QueryParameterVisitor visitor);
    boolean isTrivial();
}
