package cz.cvut.fel.task_evaluator.service.evaluation.query.parameter;

import cz.cvut.fel.task_evaluator.service.evaluation.checker.visitor.QueryParameterVisitor;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class StringLiteralParameter implements QueryParameter {
    private String value;

    @Override
    public void accept(QueryParameterVisitor visitor) {
        visitor.visitStringLiteralParameter(this);
    }
}
