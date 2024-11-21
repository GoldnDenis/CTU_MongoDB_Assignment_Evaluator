package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.insert.usage;

import cz.cvut.fel.mongodb_assignment_evaluator.service.enums.CriterionDescription;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.AssignmentCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.DocumentParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.QueryParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.StringParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.query.Query;

import java.util.ArrayList;
import java.util.List;

public class InsertOneUsedCriterion extends AssignmentCriterion {
    public InsertOneUsedCriterion() {
        super(
                CriterionDescription.INSERT_ONE_USED.getDescription(),
                CriterionDescription.INSERT_ONE_USED.getRequiredCount()
        );
    }

    @Override
    public void concreteCheck(Query query) {
        if (query.getOperation().equals("insertOne")) {
            List<QueryParameter> parameters = query.getParameters();
            if (!parameters.isEmpty()) {
                parameters.get(0).accept(this);
            }
        }
    }

    @Override
    public void visitDocumentParameter(DocumentParameter documentParameter) {
        if (!documentParameter.isTrivial()) {
            satisfied = true;
            currentCount++;
        }
    }
}
