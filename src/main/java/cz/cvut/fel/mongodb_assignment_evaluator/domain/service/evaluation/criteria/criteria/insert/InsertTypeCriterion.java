package cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.criteria.criteria.insert;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type.InsertQuery;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.criteria.bson.BsonDocumentChecker;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.criteria.EvaluationCriterion;
import org.bson.BsonType;

public class InsertTypeCriterion extends EvaluationCriterion<InsertQuery> {
    private final BsonType expectedType;

    public InsertTypeCriterion(int priority, BsonType expectedType) {
        super(InsertQuery.class, priority);
        this.expectedType = expectedType;
    }

    @Override
    protected void evaluate(InsertQuery query) {
        currentScore = query.getInsertedDocuments(false).stream()
                .filter(doc -> BsonDocumentChecker.containsValueOfType(doc, expectedType))
                .count();
    }
}
