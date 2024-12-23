package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.composite.specific.insert;

import cz.cvut.fel.mongodb_assignment_evaluator.service.enums.Criteria;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.InsertedDocumentStorage;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.AssignmentCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.DocumentParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.PipelineParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.QueryParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.query.type.InsertQuery;
import org.bson.Document;

import java.util.List;

public class InsertDocumentEmbeddedObjectsCriterion extends AssignmentCriterion<InsertQuery> {
//    private final InsertCriteriaGroup parent;

    public InsertDocumentEmbeddedObjectsCriterion(InsertedDocumentStorage documentStorage) {
        super(Criteria.INSERT_DOCUMENT_EMBEDDED_OBJECTS, InsertQuery.class, documentStorage);
//        this.parent = parent;
    }

    @Override
    public void concreteCheck(InsertQuery query) {
        for (DocumentParameter documentParameter : query.getNonTrivialInsertedDocuments()) {
            containsEmbeddedObject(documentParameter);
        }
    }

    private void containsEmbeddedObject(DocumentParameter documentParameter) {
        if (documentParameter.containsFieldValueOfType(Document.class)) {
            currentScore++;
        }
    }
}
