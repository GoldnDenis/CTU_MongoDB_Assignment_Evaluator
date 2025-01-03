package cz.cvut.fel.mongodb_assignment_evaluator.evaluation.model.parameter;

import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.model.parameter.visitor.QueryParameterVisitor;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class FunctionParameter implements QueryParameter {
    private String value;

    public FunctionParameter() {
        value = "";
    }

    @Override
    public void accept(QueryParameterVisitor visitor) {
        visitor.visitFunctionParameter(this);
    }

    @Override
    public boolean isTrivial() {
        return value.isBlank();
    }
}
