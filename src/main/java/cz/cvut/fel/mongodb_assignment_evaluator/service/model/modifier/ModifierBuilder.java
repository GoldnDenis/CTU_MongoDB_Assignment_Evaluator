package cz.cvut.fel.mongodb_assignment_evaluator.service.model.modifier;

import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.EmptyParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.QueryParameter;

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
