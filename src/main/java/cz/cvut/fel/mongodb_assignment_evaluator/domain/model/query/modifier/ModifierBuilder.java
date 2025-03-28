package cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.modifier;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.parameter.EmptyParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.parameter.QueryParameter;

public class ModifierBuilder {
    private String modifier;
    private QueryParameter parameter;

    public ModifierBuilder() {
        this.modifier = "";
        this.parameter = new EmptyParameter();
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
}
