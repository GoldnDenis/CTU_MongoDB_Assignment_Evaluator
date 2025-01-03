package cz.cvut.fel.mongodb_assignment_evaluator.evaluation.model.parameter;

import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.model.parameter.visitor.QueryParameterVisitor;

public class EmptyParameter implements QueryParameter {
    @Override
    public void accept(QueryParameterVisitor visitor) {
        visitor.visitEmptyParameter(this);
    }

    @Override
    public boolean isTrivial() {
        return true;
    }
}