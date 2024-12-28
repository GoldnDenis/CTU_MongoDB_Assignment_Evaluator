package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.composite.specific.insert.document;

import cz.cvut.fel.mongodb_assignment_evaluator.service.enums.Criteria;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.InsertedDocumentStorage;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.bson.BsonDocumentChecker;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.AssignmentCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.DocumentParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.query.type.InsertQuery;
import org.bson.BsonDocument;
import org.bson.BsonType;
import org.bson.Document;

public class InsertDocumentEmbeddedObjectsCriterion extends AssignmentCriterion<InsertQuery> {
public InsertDocumentEmbeddedObjectsCriterion(InsertedDocumentStorage documentStorage) {
        super(Criteria.INSERT_DOCUMENT_EMBEDDED_OBJECTS, InsertQuery.class, documentStorage);
    }

    @Override
    public void concreteCheck(InsertQuery query) {
        for (DocumentParameter documentParameter : query.getNonTrivialInsertedDocuments()) {
            if (BsonDocumentChecker.containsValueOfType(documentParameter.getDocument(), BsonType.DOCUMENT)) {
                currentScore++;
            }
        }
    }
}
