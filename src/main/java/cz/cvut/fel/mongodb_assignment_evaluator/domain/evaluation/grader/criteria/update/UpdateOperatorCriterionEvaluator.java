package cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.grader.criteria.update;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.enums.MongoCommands;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.enums.UpdateOperators;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.grader.criteria.CriterionEvaluator;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.entity.Criterion;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type.UpdateQuery;

/**
 * Inspects update documents or pipelines for specified operators.
 * Verifies correct command usage if specified.
 */
public class UpdateOperatorCriterionEvaluator extends CriterionEvaluator<UpdateQuery> {
    private final UpdateOperators requiredGroup;
    private final String requiredCommand;

    public UpdateOperatorCriterionEvaluator(Criterion criterion, UpdateOperators requiredGroup, MongoCommands requiredCommand) {
        super(UpdateQuery.class, criterion);
        this.requiredGroup = requiredGroup;
        this.requiredCommand = requiredCommand.getCommand();
    }

    public UpdateOperatorCriterionEvaluator(Criterion criterion, UpdateOperators requiredGroup) {
        super(UpdateQuery.class, criterion);
        this.requiredGroup = requiredGroup;
        this.requiredCommand = "";
    }

    @Override
    protected void evaluate(UpdateQuery query) {
        if (!requiredCommand.isBlank() && !query.commandEquals(requiredCommand)) {
            return;
        }
        currentScore = query.findUpdateGroup(requiredGroup).size();
    }
}
