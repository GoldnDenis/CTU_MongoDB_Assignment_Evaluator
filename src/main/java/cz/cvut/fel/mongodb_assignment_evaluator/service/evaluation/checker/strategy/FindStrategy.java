package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.strategy;

import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.MockMongoDB;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.aggregate.stage.AggregateLookupUsedCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.find.*;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.find.projection.FindNegativeProjectionCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.find.projection.FindPositiveProjectionCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.find.search.*;

public class FindStrategy extends CheckerStrategy{
    public FindStrategy(MockMongoDB mockDb) {
        super(mockDb);
        criteria.add(new FindFiveQueriesCriterion(mockDb));
        criteria.add(new FindLogicalOperatorCriterion(mockDb));
        criteria.add(new FindElemMatchUsedCriterion(mockDb));
        criteria.add(new FindPositiveProjectionCriterion(mockDb));
        criteria.add(new FindNegativeProjectionCriterion(mockDb));
        criteria.add(new FindSortModifierCriterion(mockDb));
        criteria.add(new FindValueCriterion(mockDb));
        criteria.add(new FindConditionCriterion(mockDb));
        criteria.add(new FindDateComparisonCriterion(mockDb));
        criteria.add(new FindArrayCriterion(mockDb));
        criteria.add(new FindArrayValueCriterion(mockDb));
    }
}
