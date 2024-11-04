package cz.cvut.fel.task_evaluator.model.token;

import cz.cvut.fel.task_evaluator.enums.TokenTypes;

public record Token(String value, TokenTypes type, int lineNumber, int columnNumber) {}
