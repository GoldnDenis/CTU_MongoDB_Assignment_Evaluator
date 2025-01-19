package cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.composite.leaf.types.find.logical;

import cz.cvut.fel.mongodb_assignment_evaluator.enums.Criteria;
import cz.cvut.fel.mongodb_assignment_evaluator.enums.QuerySelectors;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.InsertedDocumentStorage;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.composite.leaf.CheckerLeaf;
import cz.cvut.fel.mongodb_assignment_evaluator.application.model.query.type.FindQuery;

public class LogicalOperatorChecker extends CheckerLeaf<FindQuery> {
    public LogicalOperatorChecker(InsertedDocumentStorage documentStorage) {
        super(Criteria.FIND_LOGICAL_OPERATOR, FindQuery.class, documentStorage);
    }

    @Override
    public void concreteCheck(FindQuery query) {
        if (query.filterContainsSelector(QuerySelectors.AND, 1) ||
                query.filterContainsSelector(QuerySelectors.OR, 1) ||
                query.filterContainsSelector(QuerySelectors.NOT)) {
            currentScore++;
        }
    }
}
