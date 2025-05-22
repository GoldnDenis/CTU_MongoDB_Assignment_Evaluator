package cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.grader.criteria.insert;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.grader.bson.BsonDocumentChecker;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.grader.criteria.CriterionEvaluator;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.entity.Criterion;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type.InsertQuery;
import org.bson.BsonType;

/**
 * Counts occurrences of required data types in inserted documents.
 */
public class InsertTypeCriterionEvaluator extends CriterionEvaluator<InsertQuery> {
    private final BsonType expectedType;

    public InsertTypeCriterionEvaluator(Criterion criterion, BsonType expectedType) {
        super(InsertQuery.class, criterion);
        this.expectedType = expectedType;
    }

    @Override
    protected void evaluate(InsertQuery query) {
        currentScore = query.getInsertedDocuments(false).stream()
                .filter(doc -> BsonDocumentChecker.containsValueOfType(doc, expectedType))
                .toList().size();
    }
}
