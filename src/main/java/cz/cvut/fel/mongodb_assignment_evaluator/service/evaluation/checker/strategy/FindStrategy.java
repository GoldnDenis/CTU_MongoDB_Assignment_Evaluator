package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.strategy;

import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.MockMongoDB;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.aggregate.stage.AggregateLookupUsedCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.find.*;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.find.projection.FindNegativeProjectionCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.find.projection.FindPositiveProjectionCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.find.search.*;

import java.util.List;

public class FindStrategy extends CheckerStrategy{
    public FindStrategy(MockMongoDB mockDb) {
        super(mockDb, List.of(
                new FindFiveQueriesCriterion(mockDb),
                new FindLogicalOperatorCriterion(mockDb),
                new FindElemMatchUsedCriterion(mockDb),
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
