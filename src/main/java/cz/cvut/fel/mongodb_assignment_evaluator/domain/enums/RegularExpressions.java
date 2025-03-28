package cz.cvut.fel.mongodb_assignment_evaluator.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RegularExpressions {
    MONGODB_QUERY("db\\.([._0-9a-zA-Z]+)\\(([\\s\\S]*?)(\\)(\\.[\\s\\S]*?\\)))*;"),
    NESTED_FIELD("^[a-zA-Z]+[a-zA-Z0-9_]*(\\.(\\$(\\[[a-zA-Z0-9]*\\])?|[0-9]+))*(\\.[a-zA-Z]+[a-zA-Z0-9_]*)+"),
    ARRAY_FIELD("^[a-zA-Z]+[a-zA-Z0-9_]*(\\.[a-zA-Z]+[a-zA-Z0-9_]*)*(\\.((\\$(\\[[a-zA-Z0-9]*\\])?)|[0-9]+))+");

    private final String regex;
}
