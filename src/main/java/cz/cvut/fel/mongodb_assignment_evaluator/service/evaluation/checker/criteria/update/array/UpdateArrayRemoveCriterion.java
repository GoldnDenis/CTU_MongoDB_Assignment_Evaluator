package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.update.array;

import cz.cvut.fel.mongodb_assignment_evaluator.service.enums.CriterionDescription;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.AssignmentCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.QueryParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.StringParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.query.Query;

import java.util.ArrayList;
import java.util.List;

//todo update
public class UpdateArrayRemoveCriterion extends AssignmentCriterion {
     public UpdateArrayRemoveCriterion() {
        super(
                CriterionDescription.UPDATE_ARRAY_REMOVE.getDescription(),
                CriterionDescription.UPDATE_ARRAY_REMOVE.getRequiredCount()
        );
    }

    @Override
    public void concreteCheck(Query query) {
    }
}
