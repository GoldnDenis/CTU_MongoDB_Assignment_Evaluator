package cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.parameter;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.parameter.visitor.QueryParameterVisitor;

/**
 * Generally represents parameter tokens.
 * An element that is being visited. Interface is used to enable Visitor processing logic.
 */
public interface QueryParameter {
    void accept(QueryParameterVisitor visitor);
}
