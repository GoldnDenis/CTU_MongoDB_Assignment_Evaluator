package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.update.field;

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
public class UpdateAddFieldCriterion extends AssignmentCriterion {
    public UpdateAddFieldCriterion() {
        super(
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
        if (parameter.firstLevelContains("$set")) {
            currentCount++;
            satisfied = true;
        }
    }

    @Override
    public void visitPipelineParameter(PipelineParameter parameter) {
        if (parameter.firstLevelContains("$set")) {
            currentCount++;
            satisfied = true;
        }
    }
}
