package cz.cvut.fel.task_evaluator.service.evaluation.query.modifier;

import cz.cvut.fel.task_evaluator.service.evaluation.query.parameter.EmptyParameter;
import cz.cvut.fel.task_evaluator.service.evaluation.query.parameter.QueryParameter;

public class ModifierBuilder {
    private String modifier;
    private QueryParameter parameter;

    public ModifierBuilder() {
        reset();
    }

    public ModifierBuilder setModifier(String modifier) {
        this.modifier = modifier;
        return this;
    }

    public ModifierBuilder setParameter(QueryParameter parameter) {
        this.parameter = parameter;
        return this;
    }

    public QueryModifier build() {
        return new QueryModifier(modifier, parameter);
    }

    public void reset() {
        this.modifier = "";
        this.parameter = new EmptyParameter();
    }
}
