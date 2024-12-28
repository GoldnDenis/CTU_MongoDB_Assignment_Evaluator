package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.composite.specific.find.selector;

import cz.cvut.fel.mongodb_assignment_evaluator.service.enums.Criteria;
import cz.cvut.fel.mongodb_assignment_evaluator.service.enums.QuerySelectors;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.InsertedDocumentStorage;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.AssignmentCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.query.type.FindQuery;
import org.bson.BsonDocument;

import java.util.Optional;

public class FindConditionCriterion extends AssignmentCriterion<FindQuery> {
    public FindConditionCriterion(InsertedDocumentStorage documentStorage) {
        super(Criteria.FIND_CONDITION, FindQuery.class, documentStorage);
    }

    @Override
    protected void concreteCheck(FindQuery query) {
        if (query.filterContainsSelector(QuerySelectors.GREATER_THAN) ||
                query.filterContainsSelector(QuerySelectors.GREATER_THAN_EQUALS) ||
                query.filterContainsSelector(QuerySelectors.LESS_THAN) ||
                query.filterContainsSelector(QuerySelectors.LESS_THAN_EQUALS)) {
            currentScore++;
        }
    }
}
