package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.composite.specific.insert.document;

import cz.cvut.fel.mongodb_assignment_evaluator.service.enums.Criteria;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.InsertedDocumentStorage;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.AssignmentCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.DocumentParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.query.type.InsertQuery;

import java.util.List;

public class InsertDocumentArraysCriterion extends AssignmentCriterion<InsertQuery> {
//    private final InsertCriteriaGroup parent;
    public InsertDocumentArraysCriterion(InsertedDocumentStorage documentStorage) {
        super(Criteria.INSERT_DOCUMENT_ARRAYS, InsertQuery.class, documentStorage);
    }

    @Override
    public void concreteCheck(InsertQuery query) {
        for (DocumentParameter documentParameter : query.getNonTrivialInsertedDocuments()) {
            containsArray(documentParameter);
        }
    }

    private void containsArray(DocumentParameter documentParameter) {
        if (documentParameter.containsFieldValueOfType(List.class)) {
            currentScore++;
        }
    }
}
