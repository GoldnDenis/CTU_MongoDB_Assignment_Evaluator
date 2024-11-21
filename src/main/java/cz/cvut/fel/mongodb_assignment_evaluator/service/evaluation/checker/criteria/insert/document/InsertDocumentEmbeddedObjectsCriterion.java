package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.insert.document;

import cz.cvut.fel.mongodb_assignment_evaluator.service.enums.CriterionDescription;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.AssignmentCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.DocumentParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.PipelineParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.QueryParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.query.Query;

import java.util.List;

public class InsertDocumentEmbeddedObjectsCriterion extends AssignmentCriterion {
    public InsertDocumentEmbeddedObjectsCriterion() {
        super(
                CriterionDescription.INSERT_DOCUMENT_EMBEDDED_OBJECTS.getDescription(),
                CriterionDescription.INSERT_DOCUMENT_EMBEDDED_OBJECTS.getRequiredCount()
        );
    }

    @Override
    public void concreteCheck(Query query) {
        List<QueryParameter> queryParameters = query.getParameters();
        if (!queryParameters.isEmpty()) {
            queryParameters.get(0).accept(this);
        }
    }

    @Override
    public void visitDocumentParameter(DocumentParameter parameter) {
        if (parameter.containsEmbeddedObject()) {
            currentCount++;
            satisfied = true;
        }
    }

    @Override
    public void visitPipelineParameter(PipelineParameter parameter) {
        if (parameter.containsEmbeddedObjects()) {
            currentCount++;
            satisfied = true;
        }
    }
}
