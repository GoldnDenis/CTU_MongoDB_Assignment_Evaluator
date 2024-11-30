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

public class AggregateSumAvgAggregatorCriterion extends AssignmentCriterion {
    public AggregateSumAvgAggregatorCriterion(MockMongoDB mockDb) {
        super(
                mockDb,
                CriterionDescription.AGGREGATE_SUM_AVG_AGGREGATOR.getDescription(),
                CriterionDescription.AGGREGATE_SUM_AVG_AGGREGATOR.getRequiredCount()
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
            for (DocumentParameter documentParameter : parameter.getParameterList()) {
                if (documentParameter.containsField("$sum") ||
                        documentParameter.containsField("$avg")) {
                    currentCount++;
                    satisfied = true;
                    return;
                }
            }
        }
        currentParameterIdx++;
    }
}
