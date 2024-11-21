package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.aggregate.stage;

import cz.cvut.fel.mongodb_assignment_evaluator.service.enums.CriterionDescription;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.AssignmentCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.PipelineParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.QueryParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.StringParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.query.Query;

import java.util.ArrayList;
import java.util.List;

public class AggregateSkipStageCriterion extends AssignmentCriterion {
    public AggregateSkipStageCriterion() {
        super(
                CriterionDescription.AGGREGATE_SKIP_STAGE.getDescription(),
                CriterionDescription.AGGREGATE_SKIP_STAGE.getRequiredCount()
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
        if (parameter.firstLevelContains("$skip")) {
            currentCount++;
            satisfied = true;
        }
    }
}
