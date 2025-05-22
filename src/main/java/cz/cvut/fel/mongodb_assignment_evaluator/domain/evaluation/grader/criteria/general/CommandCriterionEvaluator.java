package cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.grader.criteria.general;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.enums.MongoCommands;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.grader.criteria.CriterionEvaluator;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.entity.Criterion;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type.MongoQuery;

/**
 * Verifies that the query is non-trivial and that the command matches the required operation.
 */
public class CommandCriterionEvaluator<Q extends MongoQuery> extends CriterionEvaluator<Q> {
    private final String expectedCommand;

    public CommandCriterionEvaluator(Class<Q> queryType, Criterion criterion, MongoCommands expectedCommand) {
        super(queryType, criterion);
        this.expectedCommand = expectedCommand.getCommand();
    }

    @Override
    public void evaluate(MongoQuery mongoQuery) {
        if (!mongoQuery.isTrivial()) {
            currentScore = mongoQuery.commandEquals(expectedCommand) ? 1 : 0;
        }
    }
}
