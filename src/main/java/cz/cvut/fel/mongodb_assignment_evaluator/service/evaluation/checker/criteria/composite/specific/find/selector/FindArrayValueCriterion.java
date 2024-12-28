package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.composite.specific.find.selector;

import cz.cvut.fel.mongodb_assignment_evaluator.service.enums.Criteria;
import cz.cvut.fel.mongodb_assignment_evaluator.service.enums.QuerySelectors;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.InsertedDocumentStorage;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.bson.BsonDocumentChecker;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.AssignmentCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.query.type.FindQuery;
import org.bson.BsonDocument;
import org.bson.BsonType;
import org.bson.BsonValue;

import java.util.*;

public class FindArrayValueCriterion extends AssignmentCriterion<FindQuery> {
    public FindArrayValueCriterion(InsertedDocumentStorage documentStorage) {
        super(Criteria.FIND_ARRAY_VALUE, FindQuery.class, documentStorage);
    }

    @Override
    protected void concreteCheck(FindQuery query) {
        if (query.filterContainsSelector(QuerySelectors.IN) ||
            query.filterContainsSelector(QuerySelectors.ALL) ||
            query.filterContainsSelector(QuerySelectors.ELEM_MATCH)) {
            currentScore++;
        }

        Optional<BsonDocument> optFoundDocument = documentStorage.findDocument(query.getCollection(), query.getFilter().getDocument());
        if (optFoundDocument.isEmpty()) {
            return;
        }

        BsonDocument foundDocument = optFoundDocument.get();
        BsonDocument filterDocument = query.getFilter().getDocument();
        for (Map.Entry<String, BsonValue> entry : foundDocument.entrySet()) {
            String key = entry.getKey();
            BsonValue value = entry.getValue();
            if (value.isArray() &&
                    filterDocument.containsKey(key) &&
                    !filterDocument.get(key).isArray()) {
                currentScore++;
            }
        }
    }
}
