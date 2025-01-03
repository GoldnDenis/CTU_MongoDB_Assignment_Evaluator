package cz.cvut.fel.mongodb_assignment_evaluator.evaluation.checker.criteria.composite.specific.aggregate.stage;

import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.model.enums.Criteria;
import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.model.enums.Stages;
import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.checker.InsertedDocumentStorage;
import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.checker.criteria.AssignmentCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.model.query.type.AggregateQuery;

public class AggregateSortStageCriterion extends AssignmentCriterion<AggregateQuery> {
    public AggregateSortStageCriterion(InsertedDocumentStorage documentStorage) {
        super(Criteria.AGGREGATE_SORT_STAGE, AggregateQuery.class, documentStorage);
    }

    @Override
    public void concreteCheck(AggregateQuery query) {
        if (query.containsStage(Stages.SORT)) {
            currentScore++;
        }
    }
}
