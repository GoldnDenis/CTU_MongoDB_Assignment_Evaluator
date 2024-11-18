package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.insert.usage;

import cz.cvut.fel.mongodb_assignment_evaluator.service.enums.CriterionDescription;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.AssignmentCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.QueryParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.StringParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.query.Query;

import java.util.ArrayList;
import java.util.List;

public class InsertManyUsedCriterion extends AssignmentCriterion {
    public InsertManyUsedCriterion() {
        super(
                CriterionDescription.INSERT_MANY_USED.getDescription(),
                CriterionDescription.INSERT_MANY_USED.getRequiredCount()
        );
    }

    @Override
    public void concreteCheck(Query query) {
        if (query.getOperation().equals("insertMany")) {
            satisfied = true;
            currentCount++;
        }
    }
}
