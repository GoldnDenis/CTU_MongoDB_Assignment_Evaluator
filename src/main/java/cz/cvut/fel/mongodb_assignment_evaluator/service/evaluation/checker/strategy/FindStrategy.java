package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.strategy;

import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.InsertedDocumentStorage;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.composite.specific.find.*;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.composite.specific.find.modifier.FindSortModifierCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.composite.specific.find.projection.FindNegativeProjectionCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.composite.specific.find.projection.FindPositiveProjectionCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.composite.specific.find.elemmatch.FindElemMatchCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.composite.specific.find.logical.FindLogicalOperatorCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.composite.specific.find.quantity.FindFiveQueriesCriterion;

import java.util.List;

public class FindStrategy extends CheckerStrategy{
    public FindStrategy(InsertedDocumentStorage mockDb) {
        super(mockDb, List.of(
                new FindFiveQueriesCriterion(mockDb),
                new FindLogicalOperatorCriterion(mockDb),
                new FindElemMatchCriterion(mockDb),
                new FindPositiveProjectionCriterion(mockDb),
                new FindNegativeProjectionCriterion(mockDb),
                new FindSortModifierCriterion(mockDb),
                new FindValueCriterion(mockDb),
                new FindConditionCriterion(mockDb),
                new FindDateComparisonCriterion(mockDb),
                new FindArrayCriterion(mockDb),
                new FindArrayValueCriterion(mockDb)
        ));
    }
}
