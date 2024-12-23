package cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter;

import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.visitor.QueryParameterVisitor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class PipelineParameter implements QueryParameter {
    private final List<DocumentParameter> parameterList;

    public PipelineParameter(List<DocumentParameter> parameterList) {
        this.parameterList = new ArrayList<>(parameterList);
    }

    @Override
    public void accept(QueryParameterVisitor visitor) {
        visitor.visitPipelineParameter(this);
    }

    @Override
    public boolean isTrivial() {
        return parameterList.isEmpty() || !containsNonTrivialDocument();
    }

    public boolean containsNonTrivialDocument() {
        for (DocumentParameter documentParameter : parameterList) {
            if (!documentParameter.isTrivial()) {
                return true;
            }
        }
        return false;
    }
}
