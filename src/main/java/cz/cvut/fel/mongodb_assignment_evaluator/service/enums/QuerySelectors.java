package cz.cvut.fel.mongodb_assignment_evaluator.service.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum QuerySelectors {
    AND("$and", false),
    OR("$or", false),
    NOT("$not", true),
    NOR("$nor", true),
    EQUALS("$eq", true),
    LESS_THAN("$lt", true),
    GREATER_THAN("$gt", true),
    LESS_THAN_EQUALS("$lte", true),
    GREATER_THAN_EQUALS("$gte", true),
    NOT_EQUALS("$ne", true),
    ELEM_MATCH("$elemMatch", true),;

    private final String value;
    private final boolean isDocument;
}
