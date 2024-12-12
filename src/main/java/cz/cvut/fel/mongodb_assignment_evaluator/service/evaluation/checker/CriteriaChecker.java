package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker;

import cz.cvut.fel.mongodb_assignment_evaluator.service.enums.QueryTypes;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.strategy.*;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.query.Query;

import java.util.*;
import java.util.stream.Collectors;

public class CriteriaChecker {
    private final MockMongoDB mockDb;

    private final List<Query> unknownQueries;
    private final FeedbackCollector feedbackCollector;

    private final Map<QueryTypes, CheckerStrategy> checkerStrategies;

    public CriteriaChecker() {
        this.mockDb = new MockMongoDB();

        this.unknownQueries = new ArrayList<>();
        this.feedbackCollector = new FeedbackCollector();
        this.checkerStrategies = Map.ofEntries(
                Map.entry(QueryTypes.CREATE_COLLECTION, new CreateCollectionStrategy(mockDb)),
                Map.entry(QueryTypes.INSERT, new InsertStrategy(mockDb)),
                Map.entry(QueryTypes.REPLACE_ONE, new ReplaceStrategy(mockDb)),
                Map.entry(QueryTypes.UPDATE, new UpdateStrategy(mockDb)),
                Map.entry(QueryTypes.FIND, new FindStrategy(mockDb)),
                Map.entry(QueryTypes.AGGREGATE, new AggregateStrategy(mockDb)),
                Map.entry(QueryTypes.GENERAL, new GeneralStrategy(mockDb))
        );
    }

    public List<String> checkQueries(List<Query> queries) {
        for (Query query : queries) {
            QueryTypes type = query.getType();
            if (Objects.requireNonNull(type) == QueryTypes.UNKNOWN) {
                unknownQueries.add(query);
                continue;
            }
            if (checkerStrategies.containsKey(type)) {
                checkerStrategies.get(type).checkCriteria(query);
                checkerStrategies.get(QueryTypes.GENERAL).checkCriteria(query);
            }
        }

        collectFeedback();
        return feedbackCollector.getFeedbackList();
    }

    private void collectFeedback() {
        feedbackCollector.addFeedback(
                "Unrecognised queries:\n" +
                        unknownQueries.stream()
                                .map(q -> q.toString() + '\n')
                                .collect(Collectors.joining())
        );

        checkerStrategies.values().forEach(strategy -> strategy.collectAllFeedback(feedbackCollector));
    }
}
