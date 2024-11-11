package cz.cvut.fel.task_evaluator.service.evaluation.query.parameter;

import cz.cvut.fel.task_evaluator.service.evaluation.checker.visitor.QueryParameterVisitor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bson.Document;

@AllArgsConstructor
@Getter
public class DocumentParameter implements QueryParameter {
    private Document document;
    private int depth;

    @Override
    public void accept(QueryParameterVisitor visitor) {
        visitor.visitDocumentParameter(this);
    }
}
