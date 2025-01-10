package cz.cvut.fel.mongodb_assignment_evaluator.exception;

import cz.cvut.fel.mongodb_assignment_evaluator.application.model.query.type.Query;

public class QueryCollectionNotExists extends RuntimeException {
    public QueryCollectionNotExists(Query query) {
        super("Collection '" + query.getCollection() + "' doesn't exist. Query='" + query.getQuery() + "'");
    }
}
