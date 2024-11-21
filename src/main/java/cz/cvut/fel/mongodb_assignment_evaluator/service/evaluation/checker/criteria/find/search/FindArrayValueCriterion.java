package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.find.search;

import cz.cvut.fel.mongodb_assignment_evaluator.service.enums.CriterionDescription;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.AssignmentCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.QueryParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.StringParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.query.Query;

import java.util.ArrayList;
import java.util.List;

public class FindArrayValueCriterion extends AssignmentCriterion {
    public FindArrayValueCriterion() {
        super(
                CriterionDescription.FIND_ARRAY_VALUE.getDescription(),
                CriterionDescription.FIND_ARRAY_VALUE.getRequiredCount()
        );
    }

    @Override
    public void concreteCheck(Query query) {
    }
}
