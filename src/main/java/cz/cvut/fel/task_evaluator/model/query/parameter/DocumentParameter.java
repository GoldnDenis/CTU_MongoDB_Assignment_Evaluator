package cz.cvut.fel.task_evaluator.model.query.parameter;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@AllArgsConstructor
@Getter
public class DocumentParameter implements QueryParameter {
    private String document;
    private int depth;
}
