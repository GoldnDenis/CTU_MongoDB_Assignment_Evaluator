package cz.cvut.fel.mongodb_assignment_evaluator.evaluation.checker.criteria.composite.specific.find.projection;

import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.model.enums.Criteria;
import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.checker.InsertedDocumentStorage;
import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.checker.criteria.AssignmentCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.model.query.type.FindQuery;

public class FindPositiveProjectionCriterion extends AssignmentCriterion<FindQuery> {
    public FindPositiveProjectionCriterion(InsertedDocumentStorage documentStorage) {
        super(Criteria.FIND_POSITIVE_PROJECTION, FindQuery.class, documentStorage);
    }

    @Override
    public void concreteCheck(FindQuery query) {
        if (query.projectionIsPositive()) {
            currentScore++;
        }
    }
}
