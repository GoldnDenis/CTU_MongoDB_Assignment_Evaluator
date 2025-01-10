package cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.composite.leaf.types.find.type;

import cz.cvut.fel.mongodb_assignment_evaluator.enums.Criteria;
import cz.cvut.fel.mongodb_assignment_evaluator.enums.QuerySelectors;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.InsertedDocumentStorage;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.bson.BsonDocumentChecker;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.composite.leaf.CheckerLeaf;
import cz.cvut.fel.mongodb_assignment_evaluator.application.model.query.type.FindQuery;
import org.bson.BsonType;

public class
FindArrayCriterionChecker extends CheckerLeaf<FindQuery> {
    public FindArrayCriterionChecker(InsertedDocumentStorage documentStorage) {
        super(  Criteria.FIND_ARRAY, FindQuery.class, documentStorage);
    }

    @Override
    protected void concreteCheck(FindQuery query) {
        if (query.filterContainsSelector(QuerySelectors.SIZE) ||
                BsonDocumentChecker.containsValueOfType(query.getFilter(), BsonType.ARRAY)) {
            currentScore++;
        }
    }
}
