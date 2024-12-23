package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.composite.specific.aggregate;

import cz.cvut.fel.mongodb_assignment_evaluator.service.enums.Criteria;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.InsertedDocumentStorage;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.AssignmentCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.DocumentParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.PipelineParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.QueryParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.query.type.Query;

import java.util.List;

public class AggregateSortStageCriterion extends AssignmentCriterion {
    public AggregateSortStageCriterion(InsertedDocumentStorage mockDb) {
        super(
                mockDb,
                Criteria.AGGREGATE_SORT_STAGE.getDescription(),
                Criteria.AGGREGATE_SORT_STAGE.getRequiredCount()
        );
    }

    @Override
    public void concreteCheck(Query query) {
        curParamIdx = 0;
        List<QueryParameter> parameters = query.getParameters();
        if (!parameters.isEmpty()) {
            parameters.get(0).accept(this);
        }
    }

    @Override
    public void visitPipelineParameter(PipelineParameter parameter) {
        if (curParamIdx == 0) {
            for (DocumentParameter documentParameter : parameter.getParameterList()) {
                if (documentParameter.containsField("$sort", 1)) {
                    currentScore++;
                    satisfied = true;
                    return;
                }
            }
        }
        curParamIdx++;
    }
}
