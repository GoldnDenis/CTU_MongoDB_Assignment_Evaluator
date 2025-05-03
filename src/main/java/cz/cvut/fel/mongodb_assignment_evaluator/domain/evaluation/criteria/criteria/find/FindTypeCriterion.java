package cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.criteria.criteria.find;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.criteria.bson.BsonDocumentChecker;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.criteria.criteria.EvaluationCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type.FindQueryToken;
import org.bson.BsonType;

public class FindTypeCriterion extends EvaluationCriterion<FindQueryToken> {
    private final BsonType requiredType;

    public FindTypeCriterion(int priority, BsonType requiredType) {
        super(FindQueryToken.class, priority);
        this.requiredType = requiredType;
    }

    @Override
    protected void evaluate(FindQueryToken query) {
        currentScore = BsonDocumentChecker.containsValueOfType(query.getFilter(), requiredType) ? 1 : 0;
    }
}
