package cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.criteria.criteria.update;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.enums.Operators;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.enums.UpdateGroups;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.criteria.criteria.EvaluationCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.entity.Criterion;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type.UpdateQueryToken;

public class UpdateGroupCriterion extends EvaluationCriterion<UpdateQueryToken> {
    private final UpdateGroups requiredGroup;
    private final String requiredOperator;

    public UpdateGroupCriterion(Criterion criterion, int priority, UpdateGroups requiredGroup, Operators requiredOperator) {
        super(UpdateQueryToken.class, criterion, priority);
        this.requiredGroup = requiredGroup;
        this.requiredOperator = requiredOperator.getOperator();
    }

    public UpdateGroupCriterion(Criterion criterion, int priority, UpdateGroups requiredGroup) {
        super(UpdateQueryToken.class, criterion, priority);
        this.requiredGroup = requiredGroup;
        this.requiredOperator = "";
    }

    @Override
    protected void evaluate(UpdateQueryToken query) {
        if (!requiredOperator.isBlank() && !query.operatorEquals(requiredOperator)) {
            return;
        }
        currentScore = query.findUpdateGroup(requiredGroup).size();
    }
}
