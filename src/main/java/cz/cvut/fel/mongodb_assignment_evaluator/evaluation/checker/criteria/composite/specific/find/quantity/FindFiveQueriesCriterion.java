package cz.cvut.fel.mongodb_assignment_evaluator.evaluation.checker.criteria.composite.specific.find.quantity;

import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.model.enums.Criteria;
import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.checker.InsertedDocumentStorage;
import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.checker.criteria.AssignmentCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.model.query.type.FindQuery;

public class FindFiveQueriesCriterion extends AssignmentCriterion<FindQuery> {
    public FindFiveQueriesCriterion(InsertedDocumentStorage documentStorage) {
        super(Criteria.FIND_FIVE_QUERIES, FindQuery.class, documentStorage);
    }

    @Override
    public void concreteCheck(FindQuery query) {
        if (!query.getFilter().isTrivial()) {
            currentScore++;
        }
    }
}
