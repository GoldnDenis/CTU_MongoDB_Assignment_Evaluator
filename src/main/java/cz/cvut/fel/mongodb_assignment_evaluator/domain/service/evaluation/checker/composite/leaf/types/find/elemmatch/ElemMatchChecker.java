package cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.checker.composite.leaf.types.find.elemmatch;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.enums.Criteria;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.enums.QuerySelectors;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type.FindQuery;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.checker.InsertedDocumentStorage;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.checker.composite.leaf.CheckerLeaf;

public class ElemMatchChecker extends CheckerLeaf<FindQuery> {
    public ElemMatchChecker(InsertedDocumentStorage documentStorage) {
        super(Criteria.ELEMMATCH_USED, FindQuery.class, documentStorage);
    }

    @Override
    public void concreteCheck(FindQuery query) {
        if (query.filterContainsSelector(QuerySelectors.ELEM_MATCH)) {
            currentScore++;
        }
    }
}
