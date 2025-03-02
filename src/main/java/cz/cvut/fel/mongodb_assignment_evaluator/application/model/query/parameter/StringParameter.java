package cz.cvut.fel.mongodb_assignment_evaluator.application.model.query.parameter;

import cz.cvut.fel.mongodb_assignment_evaluator.application.model.query.parameter.visitor.QueryParameterVisitor;
import lombok.Getter;

@Getter
public class StringParameter implements QueryParameter {
    private String value;

    public StringParameter(String value) {
        this.value = value.substring(1, value.length() - 1);
    }

    @Override
    public void accept(QueryParameterVisitor visitor) {
        visitor.visitStringParameter(this);
    }

    @Override
    public boolean isTrivial() {
        return value.isBlank();
    }
}
