package cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.checker.composite.leaf.types.find.selector;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.enums.Criteria;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.enums.QuerySelectors;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type.FindQuery;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.checker.InsertedDocumentStorage;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.checker.composite.leaf.CheckerLeaf;
import org.bson.BsonDocument;
import org.bson.BsonValue;

import java.util.Map;
import java.util.Optional;

public class FindArrayValueChecker extends CheckerLeaf<FindQuery> {
    public FindArrayValueChecker(InsertedDocumentStorage documentStorage) {
        super(Criteria.FIND_ARRAY_VALUE, FindQuery.class, documentStorage);
    }

    @Override
    protected void concreteCheck(FindQuery query) {
        if (query.filterContainsSelector(QuerySelectors.IN) ||
            query.filterContainsSelector(QuerySelectors.ALL) ||
            query.filterContainsSelector(QuerySelectors.ELEM_MATCH)) {
            currentScore++;
        }

        Optional<BsonDocument> optFoundDocument = documentStorage.findDocument(query.getCollection(), query.getFilter());
        if (optFoundDocument.isEmpty()) {
            return;
        }

        BsonDocument foundDocument = optFoundDocument.get();
        BsonDocument filterDocument = query.getFilter();
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
