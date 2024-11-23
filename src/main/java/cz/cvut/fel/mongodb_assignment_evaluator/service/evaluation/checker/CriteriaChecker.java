package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker;

import cz.cvut.fel.mongodb_assignment_evaluator.service.enums.QueryTypes;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.strategy.*;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.query.Query;

import java.util.*;

public class CriteriaChecker {
    private MockMongoDB mockDb;

    private final Map<QueryTypes, CheckerStrategy> checkerStrategies;
    private final GeneralStrategy generalStrategy;
//    private final FeedbackGenerator feedbackCollector;

    public CriteriaChecker() {
        this.mockDb = new MockMongoDB();

        this.checkerStrategies = Map.ofEntries(
                Map.entry(QueryTypes.CREATE_COLLECTION, new CreateCollectionStrategy(mockDb)),
                Map.entry(QueryTypes.INSERT, new InsertStrategy(mockDb)),
                Map.entry(QueryTypes.UPDATE, new UpdateStrategy(mockDb)),
                Map.entry(QueryTypes.REPLACE_ONE, new ReplaceStrategy(mockDb)),
                Map.entry(QueryTypes.FIND, new FindStrategy(mockDb)),
                Map.entry(QueryTypes.AGGREGATE, new AggregateStrategy(mockDb))
        );
        this.generalStrategy = new GeneralStrategy(mockDb);
    }

    public void checkQueries(List<Query> queries) {
        List<Query> unknownQueries = new ArrayList<>();
        for (Query query : queries) {
            QueryTypes type = query.getType();
            if (Objects.requireNonNull(type) == QueryTypes.UNKNOWN) {
                unknownQueries.add(query);
                continue;
            }
            generalStrategy.checkCriteria(query);
            if (checkerStrategies.containsKey(type)) {
                checkerStrategies.get(type).checkCriteria(query);
            }
        }

        System.out.println("\n==========================================");
        System.out.println("Unrecognised queries:");
        unknownQueries.forEach(System.out::println);

        generalStrategy.collectAllFeedback();
        checkerStrategies.values().forEach(CheckerStrategy::collectAllFeedback);
    }

//    public void checkQueries(List<Query> queries) {
//        System.out.println("\n==========================================");
//        System.out.println("Unrecognised queries:");
//        List<Query> unknownQueries = queries.stream()
//                .filter(q -> q.getType() == QueryTypes.UNKNOWN)
//                .peek(System.out::println)
//                .toList();
//
//        queries.stream()
//                .filter(q -> q.getType() != QueryTypes.UNKNOWN)
//                .forEach(query ->
//                        {
//                            generalStrategy.checkCriteria(query);
//                            checkerStrategies.get(query.getType()).checkCriteria(query);
//                        }
//                );
//
//        generalStrategy.collectAllFeedback();
//        checkerStrategies.values().forEach(CheckerStrategy::collectAllFeedback);
//    }
}
