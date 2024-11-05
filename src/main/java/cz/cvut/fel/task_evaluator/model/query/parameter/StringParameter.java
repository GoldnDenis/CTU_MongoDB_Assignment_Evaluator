package cz.cvut.fel.task_evaluator.model.query.parameter;

import cz.cvut.fel.task_evaluator.evaluation.checker.visitor.QueryParameterVisitor;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class StringParameter implements QueryParameter {
    private String value;

    @Override
    public void accept(QueryParameterVisitor visitor) {
        visitor.visitStringParameter(this);
    }
}
