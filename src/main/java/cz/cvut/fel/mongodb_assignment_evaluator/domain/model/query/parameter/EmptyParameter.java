package cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.parameter;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.parameter.visitor.QueryParameterVisitor;

/**
 * A placeholder for the empty left parameter in MongoDB
 */
public class EmptyParameter implements QueryParameter {
    @Override
    public void accept(QueryParameterVisitor visitor) {
        visitor.visitEmptyParameter(this);
    }
}
