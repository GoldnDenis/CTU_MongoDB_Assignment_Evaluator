package cz.cvut.fel.task_evaluator.evaluation.checker.strategy.factory;

import cz.cvut.fel.task_evaluator.enums.QueryTypes;
import cz.cvut.fel.task_evaluator.evaluation.checker.strategy.*;

public class CheckerStrategyRegistry {
    private final static GeneralStrategy generalStrategy = new GeneralStrategy();
    private final static GeneralStrategy createCollectionStrategy = new CreateCollectionStrategy();
    private final static GeneralStrategy insertStrategy = new InsertStrategy();
    private final static GeneralStrategy updateStrategy = new UpdateStrategy();
    private final static GeneralStrategy replaceOneStrategy = new ReplaceOneStrategy();
    private final static GeneralStrategy findStrategy = new FindStrategy();
    private final static GeneralStrategy aggregateStrategy = new AggregateStrategy();

    public static GeneralStrategy createGeneralStrategy(QueryTypes type) {
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
                return generalStrategy;
            }
        }
    }
}
