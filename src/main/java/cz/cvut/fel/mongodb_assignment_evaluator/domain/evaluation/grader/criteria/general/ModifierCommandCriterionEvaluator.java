package cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.grader.criteria.general;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.enums.MongoCommands;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.grader.criteria.CriterionEvaluator;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.entity.Criterion;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.modifier.QueryModifier;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type.MongoQuery;

import java.util.List;

/**
 * Verifies that the query is non-trivial and that it includes a modifier with the required command.
 */
public class ModifierCommandCriterionEvaluator<Q extends MongoQuery> extends CriterionEvaluator<Q> {
    private final String expectedCommand;

    public ModifierCommandCriterionEvaluator(Class<Q> queryType, Criterion criterion, MongoCommands expectedCommand) {
        super(queryType, criterion);
        this.expectedCommand = expectedCommand.getCommand();
    }

    @Override
    public void evaluate(MongoQuery mongoQuery) {
        List<QueryModifier> modifiers = mongoQuery.getModifiers();
        if (modifiers.isEmpty() ||
                mongoQuery.isTrivial()) {
            return;
        }
        for (QueryModifier modifier : modifiers) {
            if (modifier.getOperator().equalsIgnoreCase(expectedCommand)) {
                currentScore++;
                break;
            }
        }
    }
}
