package cz.cvut.fel.task_evaluator.evaluation.checker;


import cz.cvut.fel.task_evaluator.enums.QueryTypes;
import cz.cvut.fel.task_evaluator.evaluation.checker.strategy.*;
import cz.cvut.fel.task_evaluator.model.query.Query;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CriteriaChecker {
    private final Map<QueryTypes, CheckerStrategy> checkerStrategies;
//    private final FeedbackGenerator feedbackCollector;

    public CriteriaChecker() {
        this.checkerStrategies = new HashMap<>();
        checkerStrategies.put(QueryTypes.CREATE_COLLECTION, new CreateCollectionStrategy());
        checkerStrategies.put(QueryTypes.INSERT, new InsertStrategy());
        checkerStrategies.put(QueryTypes.UPDATE, new UpdateStrategy());
        checkerStrategies.put(QueryTypes.REPLACE_ONE, new ReplaceOneStrategy());
        checkerStrategies.put(QueryTypes.FIND, new FindStrategy());
        checkerStrategies.put(QueryTypes.AGGREGATE, new AggregateStrategy());
        checkerStrategies.put(QueryTypes.UNKNOWN, new CheckerStrategy());
    }

    public void checkQueries(List<Query> queries) {
        for (Query query : queries) {
            QueryTypes currentType = query.getType();
            if (checkerStrategies.containsKey(currentType)) {
                checkerStrategies.get(currentType).checkCriteria(query);
            } else {
                //todo cant happen, look into it
                System.err.println("Query type: " + currentType + " is not supported.");
            }
        }
        collectAllFeedback();
    }

    private void collectAllFeedback() {
        for (CheckerStrategy s : checkerStrategies.values()) {
            s.collectAllFeedback();
//            feedbackCollector.addAllFeedback(s.checkCriteria());
        }
    }
}
