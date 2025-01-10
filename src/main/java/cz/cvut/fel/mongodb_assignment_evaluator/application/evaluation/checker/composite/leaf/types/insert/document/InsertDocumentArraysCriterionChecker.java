package cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.composite.leaf.types.insert.document;

import cz.cvut.fel.mongodb_assignment_evaluator.enums.Criteria;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.InsertedDocumentStorage;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.bson.BsonDocumentChecker;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.composite.leaf.CheckerLeaf;
import cz.cvut.fel.mongodb_assignment_evaluator.application.model.query.type.InsertQuery;
import org.bson.BsonDocument;
import org.bson.BsonType;

public class InsertDocumentArraysCriterionChecker extends CheckerLeaf<InsertQuery> {
    public InsertDocumentArraysCriterionChecker(InsertedDocumentStorage documentStorage) {
        super(Criteria.INSERT_DOCUMENT_ARRAYS, InsertQuery.class, documentStorage);
    }

    @Override
    public void concreteCheck(InsertQuery query) {
        for (BsonDocument document : query.getNonTrivialInsertedDocuments()) {
            if (BsonDocumentChecker.containsValueOfType(document, BsonType.ARRAY)) {
                currentScore++;
            }
        }
    }
}
