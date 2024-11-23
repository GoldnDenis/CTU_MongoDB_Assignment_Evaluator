package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.update.array;

import cz.cvut.fel.mongodb_assignment_evaluator.service.enums.CriterionDescription;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.BsonChecker;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.MockMongoDB;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.AssignmentCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.DocumentParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.PipelineParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.QueryParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.query.Query;
import org.bson.Document;

import java.util.List;
import java.util.regex.Pattern;

//todo update
public class UpdateArrayReplaceCriterion extends AssignmentCriterion {
    private final Pattern arrayPattern = Pattern.compile("^[a-zA-Z]+[a-zA-Z0-9_]*(\\.[a-zA-Z]+[a-zA-Z0-9_]*)*(\\.\\$(\\[[a-zA-Z0-9]*\\])?|[0-9]+)+");

    public UpdateArrayReplaceCriterion(MockMongoDB mockDb) {
        super(
                mockDb,
                CriterionDescription.UPDATE_ARRAY_REPLACE.getDescription(),
                CriterionDescription.UPDATE_ARRAY_REPLACE.getRequiredCount()
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
        checkDocumentParameter(parameter);
    }

    @Override
    public void visitPipelineParameter(PipelineParameter parameter) {
        for (DocumentParameter documentParameter: parameter.getParameterList()) {
            checkDocumentParameter(documentParameter);
        }
    }

    // todo ask
    private void checkDocumentParameter(DocumentParameter parameter) {
        Object found = parameter.getValue("$set", 1);
        if (found instanceof Document) {
            String key = BsonChecker.findFieldMatchesPattern(
                    (Document) found,
                    1,
                    arrayPattern
            );
            if (!key.isBlank()) {
                currentCount++;
                satisfied = true;
//                return true;
            }
        }
//        return false;
    }
}
