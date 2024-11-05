package cz.cvut.fel.task_evaluator.evaluation.checker.strategy.factory;

import cz.cvut.fel.task_evaluator.enums.QueryTypes;
import cz.cvut.fel.task_evaluator.evaluation.checker.strategy.*;

public class CheckerStrategyRegistry {
    private final static CheckerStrategy CHECKER_STRATEGY = new CheckerStrategy();
    private final static CheckerStrategy createCollectionStrategy = new CreateCollectionStrategy();
    private final static CheckerStrategy insertStrategy = new InsertStrategy();
    private final static CheckerStrategy updateStrategy = new UpdateStrategy();
    private final static CheckerStrategy replaceOneStrategy = new ReplaceOneStrategy();
    private final static CheckerStrategy findStrategy = new FindStrategy();
    private final static CheckerStrategy aggregateStrategy = new AggregateStrategy();

    public static CheckerStrategy createGeneralStrategy(QueryTypes type) {
        switch (type) {
            case CREATE_COLLECTION -> {
                return createCollectionStrategy;
            }
            case INSERT -> {
                return insertStrategy;
            }
            case UPDATE -> {
                return updateStrategy;
            }
            case REPLACE_ONE -> {
                return replaceOneStrategy;
            }
            case FIND -> {
                return findStrategy;
            }
            case AGGREGATE -> {
                return aggregateStrategy;
            }
            default -> {
                return CHECKER_STRATEGY;
            }
        }
    }
}
