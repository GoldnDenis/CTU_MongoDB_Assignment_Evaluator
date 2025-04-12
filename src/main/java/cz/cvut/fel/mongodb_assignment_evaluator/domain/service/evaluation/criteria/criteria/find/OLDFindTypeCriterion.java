package cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.criteria.criteria.find;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type.FindQuery;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.criteria.bson.BsonDocumentChecker;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.criteria.EvaluationCriterion;
import org.bson.BsonDocument;
import org.bson.BsonType;

import java.util.HashSet;
import java.util.Set;

public class OLDFindTypeCriterion extends EvaluationCriterion<FindQuery> {
    private final BsonType expectedType;
    private final Set<String> expectedSelectors;

    public OLDFindTypeCriterion(BsonType expectedType, Set<String> expectedSelectors) {
        super(FindQuery.class, 1);
        this.expectedType = expectedType;
        this.expectedSelectors = new HashSet<>(expectedSelectors);
    }

    public OLDFindTypeCriterion(BsonType expectedType) {
        super(FindQuery.class, 1);
        this.expectedType = expectedType;
        this.expectedSelectors = new HashSet<>();
    }

    @Override
    protected void evaluate(FindQuery query) {
        BsonDocument filter = query.getFilter();
        for (String selector : expectedSelectors) {
            if (BsonDocumentChecker.getRecursive(filter, selector).isPresent()) {
                currentScore++;
                return;
            }
        }
        if (BsonDocumentChecker.containsValueOfType(filter, expectedType)) {
            currentScore++;
        }
    }
}
