package cz.cvut.fel.task_evaluator.service.evaluation.query.parameter;

import cz.cvut.fel.task_evaluator.service.evaluation.checker.visitor.QueryParameterVisitor;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class DocumentParameter implements QueryParameter {
    private String document;
    private int depth;

    @Override
    public void accept(QueryParameterVisitor visitor) {
        visitor.visitDocumentParameter(this);
    }
}
