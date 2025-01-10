package cz.cvut.fel.mongodb_assignment_evaluator.application.model.modifier;

import cz.cvut.fel.mongodb_assignment_evaluator.application.model.parameter.QueryParameter;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class QueryModifier {
    private final String modifier;
    private final QueryParameter parameter;
}
