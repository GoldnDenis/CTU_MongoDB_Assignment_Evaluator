package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.update;

import cz.cvut.fel.mongodb_assignment_evaluator.service.enums.CriterionDescription;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.BsonChecker;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.AssignmentCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.DocumentParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.PipelineParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.QueryParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.query.Query;
import org.bson.Document;

import java.util.List;

//todo update
public class UpdateOneDocumentCriterion extends AssignmentCriterion {
    public UpdateOneDocumentCriterion() {
        super(
                CriterionDescription.UPDATE_ONE_DOCUMENT.getDescription(),
                CriterionDescription.UPDATE_ONE_DOCUMENT.getRequiredCount()
        );
    }

    @Override
    public void concreteCheck(Query query) {
        if (!query.getOperator().equalsIgnoreCase("updateone")) {
            return;
        }

        List<QueryParameter> parameters = query.getParameters();
        if (parameters.size() >= 2) {
            parameters.get(1).accept(this);
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
        Document setDocument = BsonChecker.getFirstLevelOperatorDocument(document, "$set");
        if (setDocument != null && !setDocument.isEmpty()) {
            currentCount++;
            satisfied = true;
        }
    }
}
