package cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter;

import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.visitor.QueryParameterVisitor;

//public class EmptyParameter implements QueryParameter<EmptyParameter> {
public class EmptyParameter implements QueryParameter {
//    @Override
//    public EmptyParameter get() {
//        return this;
//    }

    @Override
    public void accept(QueryParameterVisitor visitor) {
        visitor.visitEmptyParameter(this);
    }
}
