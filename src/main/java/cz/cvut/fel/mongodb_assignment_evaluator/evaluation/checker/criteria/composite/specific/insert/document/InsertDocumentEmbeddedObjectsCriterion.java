package cz.cvut.fel.mongodb_assignment_evaluator.evaluation.checker.criteria.composite.specific.insert.document;

import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.model.enums.Criteria;
import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.checker.InsertedDocumentStorage;
import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.checker.bson.BsonDocumentChecker;
import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.checker.criteria.AssignmentCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.model.parameter.DocumentParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.model.query.type.InsertQuery;
import org.bson.BsonType;

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