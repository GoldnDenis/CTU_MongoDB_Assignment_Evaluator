package cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.criteria.criteria.insert;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.criteria.bson.BsonDocumentChecker;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.criteria.criteria.EvaluationCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.entity.Criterion;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type.InsertQueryToken;
import org.bson.BsonType;

public class InsertTypeCriterion extends EvaluationCriterion<InsertQueryToken> {
    private final BsonType expectedType;

    public InsertTypeCriterion(Criterion criterion, int priority, BsonType expectedType) {
        super(InsertQueryToken.class, criterion, priority);
        this.expectedType = expectedType;
    }

    @Override
    protected void evaluate(InsertQueryToken query) {
        currentScore = query.getInsertedDocuments(false).stream()
                .filter(doc -> BsonDocumentChecker.containsValueOfType(doc, expectedType))
                .toList().size();
    }
}
