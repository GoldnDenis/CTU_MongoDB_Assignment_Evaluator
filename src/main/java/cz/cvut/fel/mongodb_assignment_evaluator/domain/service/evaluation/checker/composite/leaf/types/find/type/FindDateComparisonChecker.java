package cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.checker.composite.leaf.types.find.type;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.enums.Criteria;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.enums.QuerySelectors;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type.FindQuery;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.checker.InsertedDocumentStorage;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.checker.bson.BsonDocumentChecker;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.checker.bson.BsonValueChecker;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.checker.composite.leaf.CheckerLeaf;
import org.bson.BsonValue;

import java.util.List;
import java.util.Set;

public class FindDateComparisonChecker extends CheckerLeaf<FindQuery> {
    public FindDateComparisonChecker(InsertedDocumentStorage documentStorage) {
        super(Criteria.FIND_DATE_COMPARISON, FindQuery.class, documentStorage);
    }

    @Override
    protected void concreteCheck(FindQuery query) {
        List<BsonValue> foundCompareValues = BsonDocumentChecker.getAllRecursive(query.getFilter(), Set.of(
                QuerySelectors.GREATER_THAN.getValue(),
                QuerySelectors.GREATER_THAN_EQUALS.getValue(),
                QuerySelectors.LESS_THAN.getValue(),
                QuerySelectors.LESS_THAN_EQUALS.getValue()
        ));
        if (foundCompareValues.isEmpty()) {
            return;
        }
        if (foundCompareValues.stream().anyMatch(BsonValueChecker::isISODate)) {
            currentScore++;
        }
    }
}
