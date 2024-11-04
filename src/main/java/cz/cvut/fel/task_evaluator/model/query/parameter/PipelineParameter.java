package cz.cvut.fel.task_evaluator.model.query.parameter;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class PipelineParameter implements QueryParameter {
    private final List<DocumentParameter> parameterList;

    public PipelineParameter(List<DocumentParameter> parameterList) {
        this.parameterList = new ArrayList<>(parameterList);
    }
}
