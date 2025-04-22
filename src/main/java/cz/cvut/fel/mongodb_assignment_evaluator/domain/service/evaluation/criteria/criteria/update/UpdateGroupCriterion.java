package cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.criteria.criteria.update;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.enums.Operators;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.enums.UpdateGroups;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type.UpdateQuery;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.criteria.criteria.EvaluationCriterion;

public class UpdateGroupCriterion extends EvaluationCriterion<UpdateQuery> {
    private final UpdateGroups requiredGroup;
    private final String requiredOperator;

    public UpdateGroupCriterion(int priority, UpdateGroups requiredGroup, Operators requiredOperator) {
        super(UpdateQuery.class, priority);
        this.requiredGroup = requiredGroup;
        this.requiredOperator = requiredOperator.getOperator();
    }

    public UpdateGroupCriterion(int priority, UpdateGroups requiredGroup) {
        super(UpdateQuery.class, priority);
        this.requiredGroup = requiredGroup;
        this.requiredOperator = "";
    }

    @Override
    protected void evaluate(UpdateQuery query) {
        if (!requiredOperator.isBlank() && !query.operatorEquals(requiredOperator)) {
            return;
        }
        currentScore = query.findUpdateGroup(requiredGroup).size();
    }
}
