package cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.parameter;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.parameter.visitor.QueryParameterVisitor;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Any functions defined in extended syntax of queries, e.g. JavaScript functions
 */
@AllArgsConstructor
@Getter
public class FunctionParameter implements QueryParameter {
    private String function;

    @Override
    public void accept(QueryParameterVisitor visitor) {
        visitor.visitFunctionParameter(this);
    }
}
