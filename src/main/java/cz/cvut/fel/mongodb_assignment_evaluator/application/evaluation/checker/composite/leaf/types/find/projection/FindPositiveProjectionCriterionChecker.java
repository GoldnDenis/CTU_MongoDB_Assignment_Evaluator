package cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.composite.leaf.types.find.projection;

import cz.cvut.fel.mongodb_assignment_evaluator.enums.Criteria;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.InsertedDocumentStorage;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.composite.leaf.CheckerLeaf;
import cz.cvut.fel.mongodb_assignment_evaluator.application.model.query.type.FindQuery;

public class FindPositiveProjectionCriterionChecker extends CheckerLeaf<FindQuery> {
    public FindPositiveProjectionCriterionChecker(InsertedDocumentStorage documentStorage) {
        super(Criteria.FIND_POSITIVE_PROJECTION, FindQuery.class, documentStorage);
    }

    @Override
    public void concreteCheck(FindQuery query) {
        if (query.projectionIsPositive()) {
            currentScore++;
        }
    }
}
