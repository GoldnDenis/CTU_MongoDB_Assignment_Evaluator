package cz.cvut.fel.task_evaluator.model.query.parameter;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class StringParameter implements QueryParameter {
    private String value;
}
