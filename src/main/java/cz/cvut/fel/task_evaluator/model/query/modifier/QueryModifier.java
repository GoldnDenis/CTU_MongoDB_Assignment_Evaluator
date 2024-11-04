package cz.cvut.fel.task_evaluator.model.query.modifier;

import cz.cvut.fel.task_evaluator.model.query.parameter.QueryParameter;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class QueryModifier {
    private final String modifier;
    private final QueryParameter parameter;
}
