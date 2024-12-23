package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.composite.group;

import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.InsertedDocumentStorage;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.AssignmentCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.composite.specific.aggregate.*;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.composite.specific.aggregate.AggregateCountAggregatorCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.composite.specific.aggregate.AggregateFirstLastAggregatorCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.composite.specific.aggregate.AggregateMinMaxAggregatorCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.composite.specific.aggregate.AggregateSumAvgAggregatorCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.query.type.AggregateQuery;

import java.util.List;

public class AggregateGroupCriterion extends GroupCriterion<AggregateQuery> {
    public AggregateGroupCriterion(InsertedDocumentStorage documentStorage) {
        super(documentStorage);
    }

    @Override
    protected List<AssignmentCriterion<AggregateQuery>> initCriteria() {
        return List.of(
                new AggregateFiveCriterion(documentStorage),
                new AggregateMatchStageCriterion(documentStorage),
                new AggregateGroupStageCriterion(documentStorage),
                new AggregateSortStageCriterion(documentStorage),
                new AggregateProjectAddFieldsStageCriterion(documentStorage),
                new AggregateSkipStageCriterion(documentStorage),
                new AggregateLimitStageCriterion(documentStorage),
                new AggregateSumAvgAggregatorCriterion(documentStorage),
                new AggregateCountAggregatorCriterion(documentStorage),
                new AggregateMinMaxAggregatorCriterion(documentStorage),
                new AggregateFirstLastAggregatorCriterion(documentStorage),
                new AggregateLookupUsedCriterion(documentStorage)
        );
    }
}
