package cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.composite.leaf.types.find.selector;

import cz.cvut.fel.mongodb_assignment_evaluator.enums.Criteria;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.InsertedDocumentStorage;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.composite.leaf.CheckerLeaf;
import cz.cvut.fel.mongodb_assignment_evaluator.application.model.query.type.FindQuery;
import org.bson.BsonDocument;

import java.util.Optional;

public class FindValueCriterionChecker extends CheckerLeaf<FindQuery> {
    public FindValueCriterionChecker(InsertedDocumentStorage documentStorage) {
        super(Criteria.FIND_VALUE, FindQuery.class, documentStorage);
    }

    @Override
    protected void concreteCheck(FindQuery query) {
//        if (!query.filterContainsImplicitEquality() &&
//                !query.filterContainsSelector(QuerySelectors.EQUALS) &&
//            !query.filterContainsSelector(QuerySelectors.NOT_EQUALS)) {
//            return;
//        }
//        Optional<BsonDocument> optFoundDocument = documentStorage.findDocument(query.getCollection(), query.getFilter().getDocument());
//        if (optFoundDocument.isPresent()) {
//            currentScore++;
//        }

        Optional<BsonDocument> optFoundDocument = documentStorage.findDocument(query.getCollection(), query.getFilter());
        if (optFoundDocument.isEmpty()) {
            return;
        }
        BsonDocument foundDocument = optFoundDocument.get();
        for (String field: query.getFilter().keySet()) {
            if (foundDocument.containsKey(field) &&
                    !foundDocument.get(field).isArray() &&
                    !foundDocument.get(field).isDocument()) {
                currentScore++;
            }
        }
    }
}
