package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.update;

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

public class UpdateIncreaseMultiplyCriterion extends AssignmentCriterion {
    public UpdateIncreaseMultiplyCriterion(MockMongoDB mockDb) {
        super(
                mockDb,
                CriterionDescription.UPDATE_INCREASE_MULTIPLY.getDescription(),
                CriterionDescription.UPDATE_INCREASE_MULTIPLY.getRequiredCount()
        );
    }

    @Override
    public void concreteCheck(Query query) {
        if (query.getOperator().equalsIgnoreCase("updateMany")) {
            List<QueryParameter> parameters = query.getParameters();
            if (parameters.size() >= 2) {
                parameters.get(1).accept(this);
            }
        }
    }

    public void visitDocumentParameter(DocumentParameter parameter) {
        if (!checkDocumentParameter(parameter, "$inc")) {
            checkDocumentParameter(parameter, "$mul");
        }
    }

//    @Override
//    public void visitPipelineParameter(PipelineParameter parameter) {
//        for (DocumentParameter documentParameter: parameter.getParameterList()) {
//            if (checkDocumentParameter(documentParameter, "$inc")) {
//                return;
//            }
//            if (checkDocumentParameter(documentParameter, "$mul")) {
//                return;
//            }
//        }
//    }

    private boolean checkDocumentParameter(DocumentParameter parameter, String operator) {
        Object found = parameter.getValue(operator, 1);
        if (found instanceof Document) {
            if (!((Document) found).isEmpty()) {
                currentCount++;
                satisfied = true;
                return true;
            }
        }
        return false;
    }
}
