package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker;

import cz.cvut.fel.mongodb_assignment_evaluator.service.enums.QueryTypes;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.strategy.*;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.query.Query;
import lombok.extern.java.Log;

import java.util.*;
import java.util.stream.Collectors;

@Log
public class CriteriaChecker {
    private final MockMongoDB mockDb;

    private final List<Query> unknownQueries;
    private final FeedbackCollector feedbackCollector;

    private final Map<QueryTypes, CheckerStrategy> checkerStrategies;

    public CriteriaChecker() {
        this.mockDb = new MockMongoDB();
        this.unknownQueries = new ArrayList<>();
        this.feedbackCollector = new FeedbackCollector();
        this.checkerStrategies = new LinkedHashMap<>();
        this.initStrategies();
    }

    private void initStrategies() {
        checkerStrategies.put(QueryTypes.CREATE_COLLECTION, new CreateCollectionStrategy(mockDb));
        checkerStrategies.put(QueryTypes.INSERT, new InsertStrategy(mockDb));
        checkerStrategies.put(QueryTypes.REPLACE_ONE, new ReplaceStrategy(mockDb));
        checkerStrategies.put(QueryTypes.UPDATE, new UpdateStrategy(mockDb));
        checkerStrategies.put(QueryTypes.FIND, new FindStrategy(mockDb));
        checkerStrategies.put(QueryTypes.AGGREGATE, new AggregateStrategy(mockDb));
        checkerStrategies.put(QueryTypes.GENERAL, new GeneralStrategy(mockDb));
//        checkerStrategies.put(QueryTypes.UNKNOWN, new UnknownStrategy(mockDb));
    }

    public List<String> checkQueries(List<Query> queries) {
        for (Query query : queries) {
            QueryTypes type = query.getType();
            if (type == null) {
                log.severe("Query type is null.");
            }
            if (type == QueryTypes.UNKNOWN) {
                unknownQueries.add(query);
            } else if (checkerStrategies.containsKey(type)) {
                checkerStrategies.get(QueryTypes.GENERAL).checkCriteria(query);
                checkerStrategies.get(type).checkCriteria(query);
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

        for (CheckerStrategy strategy: checkerStrategies.values()) {
            strategy.collectEvaluationResults(feedbackCollector);
        }
    }
}
