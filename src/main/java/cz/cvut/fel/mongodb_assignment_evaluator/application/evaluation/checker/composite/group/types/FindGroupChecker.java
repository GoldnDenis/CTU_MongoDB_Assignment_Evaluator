package cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.composite.group.types;

import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.InsertedDocumentStorage;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.composite.leaf.CheckerLeaf;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.composite.group.CriteriaGroupChecker;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.composite.leaf.types.find.modifier.FindSortModifierCriterionChecker;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.composite.leaf.types.find.projection.FindNegativeProjectionCriterionChecker;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.composite.leaf.types.find.projection.FindPositiveProjectionCriterionChecker;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.composite.leaf.types.find.elemmatch.FindElemMatchCriterionChecker;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.composite.leaf.types.find.logical.FindLogicalOperatorCriterionChecker;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.composite.leaf.types.find.quantity.FindFiveQueriesCriterionChecker;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.composite.leaf.types.find.selector.FindArrayValueCriterionChecker;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.composite.leaf.types.find.selector.FindConditionCriterionChecker;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.composite.leaf.types.find.selector.FindValueCriterionChecker;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.composite.leaf.types.find.type.FindArrayCriterionChecker;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.composite.leaf.types.find.type.FindDateComparisonCriterionChecker;
import cz.cvut.fel.mongodb_assignment_evaluator.application.model.query.type.FindQuery;

import java.util.List;

public class FindGroupChecker extends CriteriaGroupChecker<FindQuery> {
    public FindGroupChecker(InsertedDocumentStorage documentStorage) {
        super(documentStorage);
    }

    @Override
    protected List<CheckerLeaf<FindQuery>> initCriteria() {
        return List.of(
                new FindFiveQueriesCriterionChecker(documentStorage),
                new FindLogicalOperatorCriterionChecker(documentStorage),
                new FindElemMatchCriterionChecker(documentStorage),
                new FindPositiveProjectionCriterionChecker(documentStorage),
                new FindNegativeProjectionCriterionChecker(documentStorage),
                new FindSortModifierCriterionChecker(documentStorage),

                new FindValueCriterionChecker(documentStorage),
                new FindConditionCriterionChecker(documentStorage),
                new FindDateComparisonCriterionChecker(documentStorage),
                new FindArrayCriterionChecker(documentStorage),
                new FindArrayValueCriterionChecker(documentStorage)
        );
    }
}
