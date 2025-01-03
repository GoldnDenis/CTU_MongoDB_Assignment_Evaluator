package cz.cvut.fel.mongodb_assignment_evaluator.evaluation.checker.criteria.composite.specific.find.selector;

import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.model.enums.Criteria;
import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.checker.InsertedDocumentStorage;
import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.checker.criteria.AssignmentCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.model.query.type.FindQuery;
import org.bson.BsonDocument;

import java.util.Optional;

public class FindValueCriterion extends AssignmentCriterion<FindQuery> {
    public FindValueCriterion(InsertedDocumentStorage documentStorage) {
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

        Optional<BsonDocument> optFoundDocument = documentStorage.findDocument(query.getCollection(), query.getFilter().getDocument());
        if (optFoundDocument.isEmpty()) {
            return;
        }
        BsonDocument foundDocument = optFoundDocument.get();
        for (String field: query.getFilter().getDocument().keySet()) {
            if (foundDocument.containsKey(field) &&
                    !foundDocument.get(field).isArray() &&
                    !foundDocument.get(field).isDocument()) {
                currentScore++;
            }
        }
    }
}
