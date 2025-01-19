package cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.composite.leaf.types.find.modifier;

import cz.cvut.fel.mongodb_assignment_evaluator.enums.Criteria;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.InsertedDocumentStorage;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.composite.leaf.CheckerLeaf;
import cz.cvut.fel.mongodb_assignment_evaluator.application.model.modifier.QueryModifier;
import cz.cvut.fel.mongodb_assignment_evaluator.application.model.query.type.FindQuery;

public class FindSortModifierChecker extends CheckerLeaf<FindQuery> {
    public FindSortModifierChecker(InsertedDocumentStorage documentStorage) {
        super(Criteria.FIND_SORT_MODIFIER, FindQuery.class, documentStorage);
    }

    @Override
    public void concreteCheck(FindQuery query) {
        if (query.getFilter().isEmpty()) {
            return;
        }
        if (query.getModifiers().stream()
                .filter(m -> m.getModifier().equals("sort"))
                .map(QueryModifier::getParameter)
                .anyMatch(p -> !p.isTrivial())) {
            currentScore++;
        }
    }
}
