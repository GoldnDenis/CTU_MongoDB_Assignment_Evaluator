package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.aggregate.aggregator;

import cz.cvut.fel.mongodb_assignment_evaluator.service.enums.CriterionDescription;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.MockMongoDB;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.AssignmentCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.DocumentParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.PipelineParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.QueryParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.StringParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.query.Query;

import java.util.ArrayList;
import java.util.List;

public class AggregateMinMaxAggregatorCriterion extends AssignmentCriterion {
    public AggregateMinMaxAggregatorCriterion(MockMongoDB mockDb) {
        super(
                mockDb,
                CriterionDescription.AGGREGATE_MIN_MAX_AGGREGATOR.getDescription(),
                CriterionDescription.AGGREGATE_MIN_MAX_AGGREGATOR.getRequiredCount()
        );
    }

    @Override
    public void concreteCheck(Query query) {
        List<QueryParameter> parameters = query.getParameters();
        if (!parameters.isEmpty()) {
            parameters.get(0).accept(this);
        }
    }

    @Override
    public void visitPipelineParameter(PipelineParameter parameter) {
        for (DocumentParameter documentParameter: parameter.getParameterList()) {
            if (documentParameter.containsField("$min") ||
                    documentParameter.containsField("$max")) {
                currentCount++;
                satisfied = true;
                return;
            }
        }
    }
}
