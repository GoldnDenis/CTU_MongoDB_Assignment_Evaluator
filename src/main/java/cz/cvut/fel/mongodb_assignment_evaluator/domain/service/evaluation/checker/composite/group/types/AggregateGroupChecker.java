package cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.checker.composite.group.types;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type.AggregateQuery;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.checker.InsertedDocumentStorage;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.checker.composite.group.CriteriaGroupChecker;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.checker.composite.leaf.CheckerLeaf;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.checker.composite.leaf.types.aggregate.aggregator.CountAggregatorChecker;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.checker.composite.leaf.types.aggregate.aggregator.FirstLastAggregatorChecker;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.checker.composite.leaf.types.aggregate.aggregator.MinMaxAggregatorChecker;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.checker.composite.leaf.types.aggregate.aggregator.SumAvgAggregatorChecker;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.checker.composite.leaf.types.aggregate.interlink.InterlinkChecker;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.checker.composite.leaf.types.aggregate.quantity.AggregateFiveChecker;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.checker.composite.leaf.types.aggregate.stage.*;

import java.util.List;

public class AggregateGroupChecker extends CriteriaGroupChecker<AggregateQuery> {
    public AggregateGroupChecker(InsertedDocumentStorage documentStorage) {
        super(documentStorage);
    }

    @Override
    protected List<CheckerLeaf<AggregateQuery>> initCriteria() {
        return List.of(
                new AggregateFiveChecker(documentStorage),
                new InterlinkChecker(documentStorage),
                new MatchStageChecker(documentStorage),
                new GroupStageChecker(documentStorage),
                new SortStageChecker(documentStorage),
                new ProjectAddFieldsStageChecker(documentStorage),
                new SkipStageChecker(documentStorage),
                new LimitStageChecker(documentStorage),
                new SumAvgAggregatorChecker(documentStorage),
                new CountAggregatorChecker(documentStorage),
                new MinMaxAggregatorChecker(documentStorage),
                new FirstLastAggregatorChecker(documentStorage),
                new LookupStageChecker(documentStorage)
        );
    }
}
