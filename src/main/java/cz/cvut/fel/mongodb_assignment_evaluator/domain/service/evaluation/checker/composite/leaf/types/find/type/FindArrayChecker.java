package cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.checker.composite.leaf.types.find.type;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.enums.Criteria;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.enums.QuerySelectors;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type.FindQuery;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.checker.InsertedDocumentStorage;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.checker.bson.BsonDocumentChecker;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.checker.composite.leaf.CheckerLeaf;
import org.bson.BsonType;

public class
FindArrayChecker extends CheckerLeaf<FindQuery> {
    public FindArrayChecker(InsertedDocumentStorage documentStorage) {
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
