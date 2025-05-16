package cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.grader.criteria.find;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.grader.bson.BsonDocumentChecker;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.grader.criteria.EvaluationCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.entity.Criterion;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type.FindQueryToken;
import org.bson.BsonType;

public class FindTypeCriterion extends EvaluationCriterion<FindQueryToken> {
    private final BsonType requiredType;

    public FindTypeCriterion(Criterion criterion, BsonType requiredType) {
        super(FindQueryToken.class, criterion);
        this.requiredType = requiredType;
    }

    @Override
    protected void evaluate(FindQueryToken query) {
        currentScore = BsonDocumentChecker.containsValueOfType(query.getFilter(), requiredType) ? 1 : 0;
    }
}
