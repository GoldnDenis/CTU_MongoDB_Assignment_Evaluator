package cz.cvut.fel.mongodb_assignment_evaluator.evaluation.checker.criteria.composite.specific.find.logical;

import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.model.enums.Criteria;
import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.model.enums.QuerySelectors;
import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.checker.InsertedDocumentStorage;
import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.checker.criteria.AssignmentCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.model.query.type.FindQuery;

public class FindLogicalOperatorCriterion extends AssignmentCriterion<FindQuery> {
    public FindLogicalOperatorCriterion(InsertedDocumentStorage documentStorage) {
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
