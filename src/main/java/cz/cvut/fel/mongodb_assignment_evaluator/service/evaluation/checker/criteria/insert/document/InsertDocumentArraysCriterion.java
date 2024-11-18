package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.insert.document;

import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.BsonChecker;
import cz.cvut.fel.mongodb_assignment_evaluator.service.enums.CriterionDescription;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.AssignmentCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.DocumentParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.PipelineParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.QueryParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.query.Query;
import org.bson.Document;

import java.util.List;

public class InsertDocumentArraysCriterion extends AssignmentCriterion {
    public InsertDocumentArraysCriterion() {
        super(
                CriterionDescription.INSERT_DOCUMENT_ARRAYS.getDescription(),
                CriterionDescription.INSERT_DOCUMENT_ARRAYS.getRequiredCount()
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
        checkDocument(parameter.getDocument());
    }

    @Override
    public void visitPipelineParameter(PipelineParameter parameter) {
        for (DocumentParameter documentParameter: parameter.getParameterList()) {
            checkDocument(documentParameter.getDocument());
        }
    }

    private void checkDocument(Document document) {
        if (BsonChecker.containsArray(document)) {
            currentCount++;
            satisfied = true;
        }
    }
}
