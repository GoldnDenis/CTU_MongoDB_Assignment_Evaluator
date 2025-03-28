package cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.newcri.criteria.find;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type.FindQuery;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type.InsertQuery;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.checker.bson.BsonDocumentChecker;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.newcri.EvaluationCriterion;
import org.bson.BsonDocument;
import org.bson.BsonType;

import java.util.HashSet;
import java.util.Set;

public class FindTypeCriterion extends EvaluationCriterion<FindQuery> {
    private final BsonType expectedType;
    private final Set<String> expectedSelectors;

    public FindTypeCriterion(BsonType expectedType, Set<String> expectedSelectors) {
        super(FindQuery.class);
        this.expectedType = expectedType;
        this.expectedSelectors = new HashSet<>(expectedSelectors);
    }

    public FindTypeCriterion(BsonType expectedType) {
        super(FindQuery.class);
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
