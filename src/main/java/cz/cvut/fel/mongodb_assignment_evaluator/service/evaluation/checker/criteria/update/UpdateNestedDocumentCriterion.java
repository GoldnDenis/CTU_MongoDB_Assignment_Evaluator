package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.update;

import cz.cvut.fel.mongodb_assignment_evaluator.service.enums.CriterionDescription;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.AssignmentCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.DocumentParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.PipelineParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.QueryParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.StringParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.query.Query;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

//todo update
public class UpdateNestedDocumentCriterion extends AssignmentCriterion {
    public UpdateNestedDocumentCriterion() {
        super(
                CriterionDescription.UPDATE_NESTED_DOCUMENT.getDescription(),
                CriterionDescription.UPDATE_NESTED_DOCUMENT.getRequiredCount()
        );
    }

    @Override
    public void concreteCheck(Query query) {
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
        if (document.containsKey("$set")) {
            if (document.get("$set") instanceof Document) {
                currentCount++;
                satisfied = true;
            }
        }
    }
}
