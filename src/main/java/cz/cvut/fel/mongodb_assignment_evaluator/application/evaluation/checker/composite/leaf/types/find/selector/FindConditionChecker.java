package cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.composite.leaf.types.find.selector;

import cz.cvut.fel.mongodb_assignment_evaluator.enums.Criteria;
import cz.cvut.fel.mongodb_assignment_evaluator.enums.QuerySelectors;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.InsertedDocumentStorage;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.composite.leaf.CheckerLeaf;
import cz.cvut.fel.mongodb_assignment_evaluator.application.model.query.type.FindQuery;

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
