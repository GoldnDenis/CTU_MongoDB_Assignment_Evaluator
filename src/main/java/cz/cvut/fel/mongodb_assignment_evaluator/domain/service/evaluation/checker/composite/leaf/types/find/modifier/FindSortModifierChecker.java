package cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.checker.composite.leaf.types.find.modifier;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.enums.Criteria;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.modifier.QueryModifier;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type.FindQuery;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.checker.InsertedDocumentStorage;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.checker.composite.leaf.CheckerLeaf;

public class FindSortModifierChecker extends CheckerLeaf<FindQuery> {
    public FindSortModifierChecker(InsertedDocumentStorage documentStorage) {
        super(Criteria.FIND_MODIFIER_SORT, FindQuery.class, documentStorage);
    }

    @Override
    public void concreteCheck(FindQuery query) {
        if (query.getFilter().isEmpty()) {
            return;
        }
        if (query.getModifiers().stream()
                .filter(m -> m.getOperator().equals("sort"))
                .map(QueryModifier::getParameter)
                .anyMatch(p -> !p.isTrivial())) {
            currentScore++;
        }
    }
}
