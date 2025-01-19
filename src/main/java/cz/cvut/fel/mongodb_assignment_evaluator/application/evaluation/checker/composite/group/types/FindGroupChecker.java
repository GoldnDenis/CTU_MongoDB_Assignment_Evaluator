package cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.composite.group.types;

import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.InsertedDocumentStorage;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.composite.leaf.CheckerLeaf;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.composite.group.CriteriaGroupChecker;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.composite.leaf.types.find.modifier.FindSortModifierChecker;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.composite.leaf.types.find.projection.NegativeProjectionChecker;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.composite.leaf.types.find.projection.PositiveProjectionChecker;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.composite.leaf.types.find.elemmatch.ElemMatchChecker;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.composite.leaf.types.find.logical.LogicalOperatorChecker;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.composite.leaf.types.find.quantity.FindFiveChecker;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.composite.leaf.types.find.selector.FindArrayValueChecker;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.composite.leaf.types.find.selector.FindConditionChecker;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.composite.leaf.types.find.selector.FindValueChecker;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.composite.leaf.types.find.type.FindArrayChecker;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.composite.leaf.types.find.type.FindDateComparisonChecker;
import cz.cvut.fel.mongodb_assignment_evaluator.application.model.query.type.FindQuery;

import java.util.List;

public class FindGroupChecker extends CriteriaGroupChecker<FindQuery> {
    public FindGroupChecker(InsertedDocumentStorage documentStorage) {
        super(documentStorage);
    }

    @Override
    protected List<CheckerLeaf<FindQuery>> initCriteria() {
        return List.of(
                new FindFiveChecker(documentStorage),
                new LogicalOperatorChecker(documentStorage),
                new ElemMatchChecker(documentStorage),
                new PositiveProjectionChecker(documentStorage),
                new NegativeProjectionChecker(documentStorage),
                new FindSortModifierChecker(documentStorage),

                new FindValueChecker(documentStorage),
                new FindConditionChecker(documentStorage),
                new FindDateComparisonChecker(documentStorage),
                new FindArrayChecker(documentStorage),
                new FindArrayValueChecker(documentStorage)
        );
    }
}
