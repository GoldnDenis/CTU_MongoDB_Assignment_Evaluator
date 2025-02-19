package cz.cvut.fel.mongodb_assignment_evaluator.application.model.parameter;

import cz.cvut.fel.mongodb_assignment_evaluator.application.model.parameter.visitor.QueryParameterVisitor;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class StringParameter implements QueryParameter {
    private String string;

    @Override
    public void accept(QueryParameterVisitor visitor) {
        visitor.visitStringParameter(this);
    }

    @Override
    public boolean isTrivial() {
        return string.isBlank();
    }
}
