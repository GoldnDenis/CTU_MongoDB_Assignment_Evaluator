package cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.composite.leaf.types.find.quantity;

import cz.cvut.fel.mongodb_assignment_evaluator.enums.Criteria;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.InsertedDocumentStorage;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.composite.leaf.CheckerLeaf;
import cz.cvut.fel.mongodb_assignment_evaluator.application.model.query.type.FindQuery;

public class FindFiveQueriesCriterionChecker extends CheckerLeaf<FindQuery> {
    public FindFiveQueriesCriterionChecker(InsertedDocumentStorage documentStorage) {
        super(Criteria.FIND_FIVE_QUERIES, FindQuery.class, documentStorage);
    }

    @Override
    public void concreteCheck(FindQuery query) {
        if (!query.getFilter().isEmpty()) {
            currentScore++;
        }
    }
}
