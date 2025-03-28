package cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.checker.composite.leaf.types.insert.document;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.enums.Criteria;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type.InsertQuery;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.checker.InsertedDocumentStorage;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.checker.bson.BsonDocumentChecker;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.checker.composite.leaf.CheckerLeaf;
import org.bson.BsonDocument;
import org.bson.BsonType;

public class InsertArraysChecker extends CheckerLeaf<InsertQuery> {
    public InsertArraysChecker(InsertedDocumentStorage documentStorage) {
        super(Criteria.INSERT_DOCUMENT_ARRAY, InsertQuery.class, documentStorage);
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
