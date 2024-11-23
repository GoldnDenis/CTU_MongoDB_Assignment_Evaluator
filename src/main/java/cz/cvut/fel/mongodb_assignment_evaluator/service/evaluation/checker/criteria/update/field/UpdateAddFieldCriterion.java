package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.update.field;

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

//todo update
public class UpdateAddFieldCriterion extends AssignmentCriterion {
    public UpdateAddFieldCriterion(MockMongoDB mockDb) {
        super(
                mockDb,
                CriterionDescription.UPDATE_ADD_FIELD.getDescription(),
                CriterionDescription.UPDATE_ADD_FIELD.getRequiredCount()
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

    //todo ask about updateMany
    private void checkDocumentParameter(DocumentParameter parameter) {
        Object found = parameter.getValue("$set", 1);
        if (found instanceof Document) {
            if (!((Document) found).isEmpty()) {
                currentCount++;
                satisfied = true;
//                return true;
            }
        }
//        return false;
    }
}
