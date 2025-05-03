package cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.builder.factory;


import cz.cvut.fel.mongodb_assignment_evaluator.domain.enums.Operators;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.builder.QueryBuilder;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.builder.types.*;

public class QueryBuilderFactory {
    public static QueryBuilder create(Operators operator) {
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
                return new QueryBuilder();
            }
        }
    }
}
