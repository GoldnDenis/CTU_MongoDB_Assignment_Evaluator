package cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.grader.criteria.find;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.grader.bson.BsonDocumentChecker;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.grader.criteria.CriterionEvaluator;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.entity.Criterion;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type.FindQuery;
import org.bson.BsonType;

/**
 * Verifies that the filter document contains at least one field of the required data type.
 */
public class FindTypeCriterionEvaluator extends CriterionEvaluator<FindQuery> {
    private final BsonType requiredType;

    public FindTypeCriterionEvaluator(Criterion criterion, BsonType requiredType) {
        super(FindQuery.class, criterion);
        this.requiredType = requiredType;
    }

    @Override
    protected void evaluate(FindQuery query) {
        if (!query.isTrivial()) {
            currentScore = BsonDocumentChecker.containsValueOfType(query.getFilter(), requiredType) ? 1 : 0;
        }
    }
}
