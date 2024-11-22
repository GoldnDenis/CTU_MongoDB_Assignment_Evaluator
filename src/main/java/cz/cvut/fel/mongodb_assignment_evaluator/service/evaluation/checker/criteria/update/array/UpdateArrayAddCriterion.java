package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.update.array;

import cz.cvut.fel.mongodb_assignment_evaluator.service.enums.CriterionDescription;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.BsonChecker;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.AssignmentCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.DocumentParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.PipelineParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.QueryParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.StringParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.query.Query;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

//todo update
public class UpdateArrayAddCriterion extends AssignmentCriterion {
    private final Pattern arrayPattern = Pattern.compile("^[a-z]+[a-z0-9_]*\\.(\\$(\\[[a-z0-9]*\\])?|[0-9]+)");

    public UpdateArrayAddCriterion() {
        super(
                CriterionDescription.UPDATE_ARRAY_ADD.getDescription(),
                CriterionDescription.UPDATE_ARRAY_ADD.getRequiredCount()
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
        Document setDocument = BsonChecker.getFirstLevelOperatorDocument(document, "$set");
        if (setDocument != null && !setDocument.isEmpty()) {
            String key = BsonChecker.findFieldMatchesPattern(setDocument, arrayPattern);
            if (!key.isBlank()) {
                currentCount++;
                satisfied = true;
            }
        }
    }
}
