package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.composite.specific.aggregate.stage;

import cz.cvut.fel.mongodb_assignment_evaluator.service.enums.Criteria;
import cz.cvut.fel.mongodb_assignment_evaluator.service.enums.Stages;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.InsertedDocumentStorage;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.AssignmentCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.DocumentParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.PipelineParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.QueryParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.query.type.AggregateQuery;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.query.type.Query;

import java.util.List;

public class AggregateSkipStageCriterion extends AssignmentCriterion<AggregateQuery> {
    public AggregateSkipStageCriterion(InsertedDocumentStorage documentStorage) {
        super(Criteria.AGGREGATE_SKIP_STAGE, AggregateQuery.class, documentStorage);
    }

    @Override
    public void concreteCheck(AggregateQuery query) {
        if (query.containsStage(Stages.SKIP)) {
            currentScore++;
        }
    }
}
