package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.replace;

import cz.cvut.fel.mongodb_assignment_evaluator.service.enums.CriterionDescription;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.AssignmentCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.DocumentParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.QueryParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.StringParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.query.Query;

import java.util.ArrayList;
import java.util.List;

public class ReplaceValueCriterion extends AssignmentCriterion {
    public ReplaceValueCriterion() {
        super(
                CriterionDescription.REPLACE_VALUE.getDescription(),
                CriterionDescription.REPLACE_VALUE.getRequiredCount()
        );
    }

    @Override
    public void concreteCheck(Query query) {
        List<QueryParameter> queryParameters = query.getParameters();
        if (query.getParameters().size() >= 2) {
            queryParameters.get(1).accept(this);
        }
    }

    @Override
    public void visitDocumentParameter(DocumentParameter parameter) {
        if (!parameter.isTrivial()) {
            currentCount++;
            satisfied = true;
        }
    }
}
