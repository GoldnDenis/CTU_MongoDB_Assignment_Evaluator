package cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.builder.factory;


import cz.cvut.fel.mongodb_assignment_evaluator.domain.enums.MongoCommands;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.builder.MongoQueryBuilder;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.builder.types.*;

/**
 * Creates a corresponding to query type concrete builder
 */
public class QueryBuilderFactory {
    public static MongoQueryBuilder create(MongoCommands operator) {
        switch (operator) {
            case CREATE_COLLECTION -> {
                return new CreateCollectionBuilder();
            }
            case INSERT, INSERT_ONE, INSERT_MANY -> {
                return new InsertBuilder();
            }
            case REPLACE_ONE -> {
                return new ReplaceBuilder();
            }
            case UPDATE_ONE, UPDATE_MANY -> {
                return new UpdateBuilder();
            }
            case AGGREGATE -> {
                return new AggregateBuilder();
            }
            case FIND, FIND_ONE -> {
                return new FindBuilder();
            }
            default -> {
                return new MongoQueryBuilder();
            }
        }
    }
}
