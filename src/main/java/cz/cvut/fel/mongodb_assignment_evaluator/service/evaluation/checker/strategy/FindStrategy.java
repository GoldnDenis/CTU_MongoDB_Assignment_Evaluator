package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.strategy;

import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.find.*;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.find.projection.FindNegativeProjectionCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.find.projection.FindPositiveProjectionCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.find.search.*;

public class FindStrategy extends CheckerStrategy{
    public FindStrategy() {
        criteria.add(new FindFiveQueriesCriterion());
        criteria.add(new FindLogicalOperatorCriterion());
        criteria.add(new FindElemMatchUsedCriterion());
        criteria.add(new FindPositiveProjectionCriterion());
        criteria.add(new FindNegativeProjectionCriterion());
        criteria.add(new FindSortModifierCriterion());
        criteria.add(new FindValueCriterion());
        criteria.add(new FindConditionCriterion());
        criteria.add(new FindDateComparisonCriterion());
        criteria.add(new FindArrayCriterion());
        criteria.add(new FindArrayValueCriterion());
        criteria.add(new FindLookupUsedCriterion());
    }
}
