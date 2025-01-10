package cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.composite.group.types;

import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.InsertedDocumentStorage;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.composite.leaf.CheckerLeaf;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.composite.group.CriteriaGroupChecker;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.composite.leaf.types.aggregate.aggregator.AggregateCountAggregatorCriterionChecker;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.composite.leaf.types.aggregate.aggregator.AggregateFirstLastAggregatorCriterionChecker;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.composite.leaf.types.aggregate.aggregator.AggregateMinMaxAggregatorCriterionChecker;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.composite.leaf.types.aggregate.aggregator.AggregateSumAvgAggregatorCriterionChecker;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.composite.leaf.types.aggregate.interlink.AggregateInterlinkCriterionChecker;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.composite.leaf.types.aggregate.quantity.AggregateFiveCriterionChecker;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.composite.leaf.types.aggregate.stage.*;
import cz.cvut.fel.mongodb_assignment_evaluator.application.model.query.type.AggregateQuery;

import java.util.List;

public class AggregateGroupChecker extends CriteriaGroupChecker<AggregateQuery> {
    public AggregateGroupChecker(InsertedDocumentStorage documentStorage) {
        super(documentStorage);
    }

    @Override
    protected List<CheckerLeaf<AggregateQuery>> initCriteria() {
        return List.of(
                new AggregateFiveCriterionChecker(documentStorage),
                new AggregateInterlinkCriterionChecker(documentStorage),
                new AggregateMatchStageCriterionChecker(documentStorage),
                new AggregateGroupStageCriterionChecker(documentStorage),
                new AggregateSortStageCriterionChecker(documentStorage),
                new AggregateProjectAddFieldsStageCriterionChecker(documentStorage),
                new AggregateSkipStageCriterionChecker(documentStorage),
                new AggregateLimitStageCriterionChecker(documentStorage),
                new AggregateSumAvgAggregatorCriterionChecker(documentStorage),
                new AggregateCountAggregatorCriterionChecker(documentStorage),
                new AggregateMinMaxAggregatorCriterionChecker(documentStorage),
                new AggregateFirstLastAggregatorCriterionChecker(documentStorage),
                new AggregateLookupStageCriterionChecker(documentStorage)
        );
    }
}
