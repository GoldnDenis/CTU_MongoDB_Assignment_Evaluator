package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.composite.group;

import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.InsertedDocumentStorage;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.AssignmentCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.composite.specific.find.*;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.composite.specific.find.modifier.FindSortModifierCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.composite.specific.find.projection.FindNegativeProjectionCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.composite.specific.find.projection.FindPositiveProjectionCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.composite.specific.find.elemmatch.FindElemMatchCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.composite.specific.find.logical.FindLogicalOperatorCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.composite.specific.find.quantity.FindFiveQueriesCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.query.type.FindQuery;

import java.util.List;

public class FindGroupCriterion extends GroupCriterion<FindQuery> {
    public FindGroupCriterion(InsertedDocumentStorage documentStorage) {
        super(documentStorage);
    }

    @Override
    protected List<AssignmentCriterion<FindQuery>> initCriteria() {
        return List.of(
                new FindFiveQueriesCriterion(documentStorage),
                new FindLogicalOperatorCriterion(documentStorage),
                new FindElemMatchCriterion(documentStorage),
                new FindPositiveProjectionCriterion(documentStorage),
                new FindNegativeProjectionCriterion(documentStorage),
                new FindSortModifierCriterion(documentStorage),
                new FindValueCriterion(documentStorage),
                new FindConditionCriterion(documentStorage),
                new FindDateComparisonCriterion(documentStorage),
                new FindArrayCriterion(documentStorage),
                new FindArrayValueCriterion(documentStorage)
        );
    }
}
