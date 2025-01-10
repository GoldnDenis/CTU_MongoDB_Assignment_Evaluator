package cz.cvut.fel.mongodb_assignment_evaluator.exception;

import cz.cvut.fel.mongodb_assignment_evaluator.enums.QueryTypes;

public class NotDefinedCriteriaQueryType extends RuntimeException {
    public NotDefinedCriteriaQueryType(QueryTypes type) {
        super("Criteria for Query type '" + type + "' are not defined");
    }
}
