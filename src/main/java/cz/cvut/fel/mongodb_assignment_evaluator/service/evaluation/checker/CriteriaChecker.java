package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker;

import cz.cvut.fel.mongodb_assignment_evaluator.service.enums.QueryTypes;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.strategy.*;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.query.Query;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CriteriaChecker {
    private final Map<QueryTypes, CheckerStrategy> checkerStrategies;
    private final GeneralStrategy generalStrategy;
//    private final FeedbackGenerator feedbackCollector;

    public CriteriaChecker() {
        this.checkerStrategies = new HashMap<>();
        checkerStrategies.put(QueryTypes.CREATE_COLLECTION, new CreateCollectionStrategy());
        checkerStrategies.put(QueryTypes.INSERT, new InsertStrategy());
        checkerStrategies.put(QueryTypes.UPDATE, new UpdateStrategy());
        checkerStrategies.put(QueryTypes.REPLACE_ONE, new ReplaceStrategy());
        checkerStrategies.put(QueryTypes.FIND, new FindStrategy());
        checkerStrategies.put(QueryTypes.AGGREGATE, new AggregateStrategy());
        this.generalStrategy = new GeneralStrategy();
    }

    public void checkQueries(List<Query> queries) {
//        List<Query> unknownQueries = queries.stream()
//                .filter(q -> q.getType() == QueryTypes.UNKNOWN)
//                .toList();

        System.out.println("\n==========================================");
        System.out.println("Unrecognised queries:");
        queries.stream()
                .filter(q -> q.getType() == QueryTypes.UNKNOWN)
                .forEach(System.out::println);

        queries.stream()
                .filter(q -> q.getType() != QueryTypes.UNKNOWN)
                .forEach(query ->
                        {
                            generalStrategy.checkCriteria(query);
                            checkerStrategies.get(query.getType()).checkCriteria(query);
                        }
                );

        generalStrategy.collectAllFeedback();
        checkerStrategies.values().forEach(CheckerStrategy::collectAllFeedback);
//        generalStrategy.collectAllFeedback();
    }


//    public void checkQueries(List<Query> queries) {
    //        for (Query query : queries) {
//            System.out.println("\n===================");
//            System.out.println(query);
//            QueryTypes currentType = query.getType();
//            generalStrategy.checkCriteria(query);
//            if (checkerStrategies.containsKey(currentType)) {
//                checkerStrategies.get(currentType).checkCriteria(query);
//            }
//        }
//    }
}
