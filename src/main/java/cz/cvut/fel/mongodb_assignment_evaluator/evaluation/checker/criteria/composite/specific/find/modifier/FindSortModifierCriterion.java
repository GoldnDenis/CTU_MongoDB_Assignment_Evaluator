package cz.cvut.fel.mongodb_assignment_evaluator.evaluation.checker.criteria.composite.specific.find.modifier;

import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.model.enums.Criteria;
import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.checker.InsertedDocumentStorage;
import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.checker.criteria.AssignmentCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.model.modifier.QueryModifier;
import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.model.query.type.FindQuery;

public class FindSortModifierCriterion extends AssignmentCriterion<FindQuery> {
    public FindSortModifierCriterion(InsertedDocumentStorage documentStorage) {
        super(Criteria.FIND_SORT_MODIFIER, FindQuery.class, documentStorage);
    }

    @Override
    public void concreteCheck(FindQuery query) {
        if (query.getFilter().isTrivial()) {
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