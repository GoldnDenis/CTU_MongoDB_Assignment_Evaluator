package cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.composite.leaf.types.find.elemmatch;

import cz.cvut.fel.mongodb_assignment_evaluator.enums.Criteria;
import cz.cvut.fel.mongodb_assignment_evaluator.enums.QuerySelectors;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.InsertedDocumentStorage;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.composite.leaf.CheckerLeaf;
import cz.cvut.fel.mongodb_assignment_evaluator.application.model.query.type.FindQuery;

public class FindElemMatchCriterionChecker extends CheckerLeaf<FindQuery> {
    public FindElemMatchCriterionChecker(InsertedDocumentStorage documentStorage) {
        super(Criteria.FIND_ELEM_MATCH, FindQuery.class, documentStorage);
    }

    @Override
    public void concreteCheck(FindQuery query) {
        if (query.filterContainsSelector(QuerySelectors.ELEM_MATCH)) {
            currentScore++;
        }
    }
}
