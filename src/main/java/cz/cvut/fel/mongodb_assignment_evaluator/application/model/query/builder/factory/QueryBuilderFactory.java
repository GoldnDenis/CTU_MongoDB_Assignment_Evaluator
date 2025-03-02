package cz.cvut.fel.mongodb_assignment_evaluator.application.model.query.builder.factory;


import cz.cvut.fel.mongodb_assignment_evaluator.application.model.query.builder.*;
import cz.cvut.fel.mongodb_assignment_evaluator.application.model.query.builder.types.*;
import cz.cvut.fel.mongodb_assignment_evaluator.enums.QueryTypes;

public class QueryBuilderFactory {
    public static QueryBuilder create(QueryTypes type) {
        switch (type) {
            case CREATE_COLLECTION -> {
                return new CreateCollectionBuilder();
            }
            case INSERT -> {
                return new InsertBuilder();
            }
            case REPLACE_ONE -> {
                return new ReplaceOneBuilder();
            }
            case UPDATE -> {
                return new UpdateBuilder();
            }
            case AGGREGATE -> {
                return new AggregateBuilder();
            }
            case FIND -> {
                return new FindBuilder();
            }
            default -> {
                return new QueryBuilder();
            }
        }
    }
}
