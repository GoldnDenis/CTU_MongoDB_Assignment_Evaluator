package cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.newcri.criteria.insert;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type.InsertQuery;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.checker.bson.BsonDocumentChecker;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.newcri.EvaluationCriterion;
import org.bson.BsonDocument;
import org.bson.BsonType;

public class InsertTypeCriterion extends EvaluationCriterion<InsertQuery> {
    private final BsonType expectedType;

    public InsertTypeCriterion(BsonType expectedType) {
        super(InsertQuery.class);
        this.expectedType = expectedType;
    }

    @Override
    protected void evaluate(InsertQuery query) {
        for (BsonDocument document: query.getInsertedDocuments(false)) {
            if (BsonDocumentChecker.containsValueOfType(document, expectedType)) {
                currentScore++;
            }
        }
    }
}
