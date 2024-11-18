package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.find;

import cz.cvut.fel.mongodb_assignment_evaluator.service.enums.CriterionDescription;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.AssignmentCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.QueryParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.StringParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.query.Query;

import java.util.ArrayList;
import java.util.List;

public class FindLookupUsedCriterion extends AssignmentCriterion {
    public FindLookupUsedCriterion() {
        super(
                CriterionDescription.CREATE_ONE_COLLECTION.getDescription(),
                CriterionDescription.CREATE_ONE_COLLECTION.getRequiredCount()
        );
    }

    @Override
    public void concreteCheck(Query query) {
    }
}
