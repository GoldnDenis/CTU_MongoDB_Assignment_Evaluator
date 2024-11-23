package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.insert.document;

import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.BsonChecker;
import cz.cvut.fel.mongodb_assignment_evaluator.service.enums.CriterionDescription;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.MockMongoDB;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.AssignmentCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.DocumentParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.PipelineParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.QueryParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.query.Query;
import org.bson.Document;

import java.util.List;

public class InsertDocumentArraysCriterion extends AssignmentCriterion {
    public InsertDocumentArraysCriterion(MockMongoDB mockDb) {
        super(
                mockDb,
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
        if (parameter.containsFieldValueOfType(List.class)) {
            currentCount++;
            satisfied = true;
        }
    }

    @Override
    public void visitPipelineParameter(PipelineParameter parameter) {
        for (DocumentParameter documentParameter: parameter.getParameterList()) {
            if (documentParameter.containsFieldValueOfType(List.class)) {
                currentCount++;
                satisfied = true;
            }
        }
    }
}
