package cz.cvut.fel.mongodb_assignment_evaluator.evaluation.checker.criteria.composite.specific.find.selector;

import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.model.enums.Criteria;
import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.model.enums.QuerySelectors;
import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.checker.InsertedDocumentStorage;
import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.checker.criteria.AssignmentCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.model.query.type.FindQuery;

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
