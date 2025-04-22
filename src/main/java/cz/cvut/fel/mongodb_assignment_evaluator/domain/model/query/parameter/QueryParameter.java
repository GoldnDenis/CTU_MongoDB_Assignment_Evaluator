package cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.parameter;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.parameter.visitor.QueryParameterVisitor;

public interface QueryParameter {
    void accept(QueryParameterVisitor visitor);

    boolean isTrivial();
}
