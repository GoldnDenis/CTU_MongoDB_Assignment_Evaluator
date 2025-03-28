package cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.newcri.criteria.find;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type.FindQuery;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.checker.bson.BsonDocumentChecker;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.newcri.EvaluationCriterion;
import org.bson.BsonDocument;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FindSelectorCriterion extends EvaluationCriterion<FindQuery> {
    private final Set<String> expectedSelectors;

    public FindSelectorCriterion(Set<String> expectedSelectors) {
        super(FindQuery.class);
        this.expectedSelectors = new HashSet<>(expectedSelectors);
    }

    // todo revisit scoring logic
    @Override
    protected void evaluate(FindQuery query) {
        if (query.isTrivial()) {
            return;
        }
        BsonDocument filter = query.getFilter();
        for (String selector: expectedSelectors) {
            boolean found = false;
            if (selector.equalsIgnoreCase("$and") || selector.equalsIgnoreCase("$or")) {
                found = processAndOr(selector, filter);
            } else {
                found = processSelector(selector, filter);
            }
            if (found) {
                currentScore++;
                break;
            }
        }
    }

    private boolean processSelector(String selector, BsonDocument filter) {
        return BsonDocumentChecker.getRecursive(filter, selector).isPresent();
    }

    private boolean processAndOr(String selector, BsonDocument filter) {
        return BsonDocumentChecker.getRecursive(filter, selector, 1).isPresent();
    }
}
