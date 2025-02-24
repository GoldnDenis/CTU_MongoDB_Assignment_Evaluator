package cz.cvut.fel.mongodb_assignment_evaluator.enums;

public enum ParserStates {
    INITIAL,
    SCRIPT,
    SINGLE_LINE_COMMENT,
    MULTI_LINE_COMMENT,
    DOT,
    QUERY_BODY,
    QUERY_END,
    STRING,
    QUERY_PARAMETER,
    FUNCTION,
    DOCUMENT,
}
