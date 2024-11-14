package cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter;

import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.visitor.QueryParameterVisitor;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class FunctionParameter implements QueryParameter {
    private String value;

    @Override
    public void accept(QueryParameterVisitor visitor) {
        visitor.visitStringLiteralParameter(this);
    }
}
