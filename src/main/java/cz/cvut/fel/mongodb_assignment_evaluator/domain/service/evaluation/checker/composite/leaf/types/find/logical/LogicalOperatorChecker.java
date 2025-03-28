package cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.checker.composite.leaf.types.find.logical;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.enums.Criteria;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.enums.QuerySelectors;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type.FindQuery;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.checker.InsertedDocumentStorage;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.checker.composite.leaf.CheckerLeaf;

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
