package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.insert.document;

import cz.cvut.fel.mongodb_assignment_evaluator.service.enums.Criteria;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.MockMongoDB;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.AssignmentCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.DocumentParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.PipelineParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.QueryParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.query.Query;
import org.bson.Document;

import java.util.List;

public class InsertDocumentEmbeddedObjectsCriterion extends AssignmentCriterion {
    public InsertDocumentEmbeddedObjectsCriterion(MockMongoDB mockDb) {
        super(
                mockDb,
                Criteria.INSERT_DOCUMENT_EMBEDDED_OBJECTS.getDescription(),
                Criteria.INSERT_DOCUMENT_EMBEDDED_OBJECTS.getRequiredCount()
        );
    }

    @Override
    public void concreteCheck(Query query) {
        curParamIdx = 0;
        List<QueryParameter> queryParameters = query.getParameters();
        if (!queryParameters.isEmpty()) {
            queryParameters.get(0).accept(this);
        }
    }

    @Override
    public void visitDocumentParameter(DocumentParameter parameter) {
        if (curParamIdx == 0) {
            containsEmbeddedObject(parameter);
        }
        curParamIdx++;
    }

    @Override
    public void visitPipelineParameter(PipelineParameter parameter) {
        if (curParamIdx == 0) {
            for (DocumentParameter documentParameter : parameter.getParameterList()) {
                containsEmbeddedObject(documentParameter);
            }
        }
        curParamIdx++;
    }

    private void containsEmbeddedObject(DocumentParameter documentParameter) {
        if (documentParameter.containsFieldValueOfType(Document.class)) {
            currentCount++;
            satisfied = true;
        }
    }
}
