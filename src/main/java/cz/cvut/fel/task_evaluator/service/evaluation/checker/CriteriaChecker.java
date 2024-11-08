package cz.cvut.fel.task_evaluator.service.evaluation.checker;


import cz.cvut.fel.task_evaluator.service.enums.QueryTypes;
import cz.cvut.fel.task_evaluator.service.evaluation.checker.strategy.*;
import cz.cvut.fel.task_evaluator.service.evaluation.query.Query;

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
        checkerStrategies.put(QueryTypes.REPLACE_ONE, new ReplaceOneStrategy());
        checkerStrategies.put(QueryTypes.FIND, new FindStrategy());
        checkerStrategies.put(QueryTypes.AGGREGATE, new AggregateStrategy());
        this.generalStrategy = new GeneralStrategy();
    }

    public void checkQueries(List<Query> queries) {
        for (Query query : queries) {
            QueryTypes currentType = query.getType();
            generalStrategy.checkCriteria(query);
            if (checkerStrategies.containsKey(currentType)) {
                checkerStrategies.get(currentType).checkCriteria(query);
            }
        }
        collectAllFeedback();
    }

    private void collectAllFeedback() {
        generalStrategy.collectAllFeedback();
        for (CheckerStrategy s : checkerStrategies.values()) {
            s.collectAllFeedback();
//            feedbackCollector.addAllFeedback(s.checkCriteria());
        }
    }
}
