package cz.cvut.fel.mongodb_assignment_evaluator.evaluation.checker.criteria.composite.specific.find.type;

import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.model.enums.Criteria;
import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.model.enums.QuerySelectors;
import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.checker.InsertedDocumentStorage;
import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.checker.bson.BsonDocumentChecker;
import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.checker.bson.BsonValueChecker;
import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.checker.criteria.AssignmentCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.model.query.type.FindQuery;
import org.bson.BsonValue;

import java.util.*;

public class FindDateComparisonCriterion extends AssignmentCriterion<FindQuery> {
    public FindDateComparisonCriterion(InsertedDocumentStorage documentStorage) {
        super(Criteria.FIND_DATE_COMPARISON, FindQuery.class, documentStorage);
    }

    @Override
    protected void concreteCheck(FindQuery query) {
        List<BsonValue> foundCompareValues = BsonDocumentChecker.getAllRecursive(query.getFilter().getDocument(), Set.of(
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