package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.aggregate.stage;

import cz.cvut.fel.mongodb_assignment_evaluator.service.enums.CriterionDescription;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.MockMongoDB;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.AssignmentCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.DocumentParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.PipelineParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.QueryParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.query.Query;

import java.util.List;

public class AggregateLookupUsedCriterion extends AssignmentCriterion {
    public AggregateLookupUsedCriterion(MockMongoDB mockDb) {
        super(
                mockDb,
                CriterionDescription.AGGREGATE_LOOKUP_USED.getDescription(),
                CriterionDescription.AGGREGATE_LOOKUP_USED.getRequiredCount()
        );
    }

    @Override
    public void concreteCheck(Query query) {
        currentParameterIdx = 0;
        List<QueryParameter> parameters = query.getParameters();
        if (!parameters.isEmpty()) {
            parameters.get(0).accept(this);
        }
    }

    @Override
    public void visitPipelineParameter(PipelineParameter parameter) {
        if (currentParameterIdx == 0) {
            for (DocumentParameter documentParameter: parameter.getParameterList()) {
                if (documentParameter.containsField("$lookup", 1)) {
                    currentCount++;
                    satisfied = true;
                    return;
                }
            }
        }
        currentParameterIdx++;
    }
}
