package cz.cvut.fel.mongodb_assignment_evaluator.application.model.query.parameter;

import cz.cvut.fel.mongodb_assignment_evaluator.application.model.query.parameter.visitor.QueryParameterVisitor;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class FunctionParameter implements QueryParameter {
    private String function;

    @Override
    public void accept(QueryParameterVisitor visitor) {
        visitor.visitFunctionParameter(this);
    }

    @Override
    public boolean isTrivial() {
        return function.isBlank();
    }
}
