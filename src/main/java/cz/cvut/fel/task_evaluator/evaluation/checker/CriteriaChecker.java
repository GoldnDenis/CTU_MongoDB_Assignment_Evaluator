package cz.cvut.fel.task_evaluator.evaluation.checker;


import cz.cvut.fel.task_evaluator.evaluation.checker.strategy.factory.CheckerStrategyRegistry;
import cz.cvut.fel.task_evaluator.model.query.Query;

import java.util.List;

public class CriteriaChecker {
    public static void checkQueries(List<Query> queryList) {
        for (Query query : queryList) {
            CheckerStrategyRegistry.createGeneralStrategy(query.getType()).checkCriteria(query);
        }

//        collectAllFeedback();
    }

//    private void collectAllFeedback() {
//        for (CheckerStrategy s : strategyList.values()) {
//            feedbackCollector.addAllFeedback(s.checkCriteria());
//        }
//    }
//
//    public void outputAllFeedback() {
//        feedbackCollector.outputAllFeedback();
//    }
}
