package cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.modifier;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.parameter.QueryParameter;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class QueryModifier {
    private final String operator;
    private final QueryParameter parameter;
}
