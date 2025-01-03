package cz.cvut.fel.mongodb_assignment_evaluator.evaluation.checker.criteria.composite.specific.find.type;

import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.model.enums.Criteria;
import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.model.enums.QuerySelectors;
import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.checker.InsertedDocumentStorage;
import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.checker.bson.BsonDocumentChecker;
import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.checker.criteria.AssignmentCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.model.query.type.FindQuery;
import org.bson.BsonType;

public class
FindArrayCriterion extends AssignmentCriterion<FindQuery> {
    public FindArrayCriterion(InsertedDocumentStorage documentStorage) {
        super(  Criteria.FIND_ARRAY, FindQuery.class, documentStorage);
    }

    @Override
    protected void concreteCheck(FindQuery query) {
        if (query.filterContainsSelector(QuerySelectors.SIZE) ||
                BsonDocumentChecker.containsValueOfType(query.getFilter().getDocument(), BsonType.ARRAY)) {
            currentScore++;
        }
    }
}
