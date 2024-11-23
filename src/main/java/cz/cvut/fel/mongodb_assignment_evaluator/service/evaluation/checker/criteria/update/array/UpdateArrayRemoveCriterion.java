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

//todo update
public class UpdateArrayRemoveCriterion extends AssignmentCriterion {
     public UpdateArrayRemoveCriterion(MockMongoDB mockDb) {
         super(
                 mockDb,
                CriterionDescription.UPDATE_ARRAY_REMOVE.getDescription(),
                CriterionDescription.UPDATE_ARRAY_REMOVE.getRequiredCount()
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
        Object found = parameter.getValue("$pull", 1);
        if (found instanceof Document) {
            if (!((Document) found).isEmpty()) {
                currentCount++;
                satisfied = true;
            }
        }
    }

//    @Override
//    public void visitPipelineParameter(PipelineParameter parameter) {
//        for (DocumentParameter documentParameter: parameter.getParameterList()) {
//            checkDocumentParameter(documentParameter);
//        }
//    }
}
