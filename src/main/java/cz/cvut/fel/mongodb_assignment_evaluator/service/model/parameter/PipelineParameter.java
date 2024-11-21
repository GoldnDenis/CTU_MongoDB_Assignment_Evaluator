package cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter;

import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.visitor.QueryParameterVisitor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
//public class PipelineParameter implements QueryParameter<PipelineParameter> {
public class PipelineParameter implements QueryParameter {
    private final List<DocumentParameter> parameterList;

    public PipelineParameter(List<DocumentParameter> parameterList) {
        this.parameterList = new ArrayList<>(parameterList);
    }

//    @Override
//    public PipelineParameter get() {
//        return this;
//    }

    @Override
    public void accept(QueryParameterVisitor visitor) {
        visitor.visitPipelineParameter(this);
    }

    public boolean containsNonTrivialDocument() {
        for (DocumentParameter documentParameter : parameterList) {
            if (!documentParameter.isTrivial()) {
                return true;
            }
        }
        return false;
    }

    public boolean containsArray() {
        for (DocumentParameter documentParameter : parameterList) {
            if (documentParameter.containsArray()) {
                return true;
            }
        }
        return false;
    }

    public boolean containsEmbeddedObjects() {
        for (DocumentParameter documentParameter : parameterList) {
            if (documentParameter.containsEmbeddedObject()) {
                return true;
            }
        }
        return false;
    }

    public boolean contains(String fieldName) {
        for (DocumentParameter documentParameter : parameterList) {
            if (documentParameter.contains(fieldName)) {
                return true;
            }
        }
        return false;
    }

    public boolean firstLevelContains(String fieldName) {
        for (DocumentParameter documentParameter : parameterList) {
            if (documentParameter.firstLevelContains(fieldName)) {
                return true;
            }
        }
        return false;
    }
}
