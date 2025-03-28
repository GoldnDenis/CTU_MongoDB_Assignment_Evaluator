package cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.checker.composite.leaf.types.find.selector;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.enums.Criteria;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.enums.QuerySelectors;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type.FindQuery;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.checker.InsertedDocumentStorage;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.checker.composite.leaf.CheckerLeaf;

public class FindConditionChecker extends CheckerLeaf<FindQuery> {
    public FindConditionChecker(InsertedDocumentStorage documentStorage) {
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
