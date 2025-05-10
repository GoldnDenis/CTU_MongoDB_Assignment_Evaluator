package cz.cvut.fel.mongodb_assignment_evaluator.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RegularExpressions {
    //    MONGODB_QUERY("db\\.([._0-9a-zA-Z]+)\\(([\\s\\S]*?)(\\)(\\.[\\s\\S]*?\\)))*;"),
    MONGODB_QUERY_START("db\\s*\\.\\s*[_0-9a-zA-Z]+(\\.\\s*[_0-9a-zA-Z]+)?"),
    NESTED_MODIFIER("\\.\\s*[_0-9a-zA-Z]+"),
    VARIABLE_CALL("[a-zA-Z0-9_-]+([\\s]*\\.[a-zA-Z0-9_-]+)*\\["),
    DATE_FIELD("((new Date)|(ISODate))\\((((\"|')([0-9]+-[0-9]+-[0-9]+)(\"|'))|(.*?(\\((.*?)\\).*?)?))\\)"),
    NESTED_FIELD("^[a-zA-Z]+[a-zA-Z0-9_]*(\\.(\\$(\\[[a-zA-Z0-9]*\\])?|[0-9]+))*(\\.[a-zA-Z]+[a-zA-Z0-9_]*)+"),
    ARRAY_FIELD("^[a-zA-Z]+[a-zA-Z0-9_]*(\\.[a-zA-Z]+[a-zA-Z0-9_]*)*(\\.((\\$(\\[[a-zA-Z0-9]*\\])?)|[0-9]+))+"),
    MONGO_SHELL_STDERR("^([a-zA-Z]*(Error|WARNING):)"),
    ID_FUNCTION("(ObjectId|UUID)\\([\"']?[a-zA-Z0-9-]*[\"']?\\)"),
    ARITHMETIC_EXPRESSION("[0-9]+(\\s*[\\+\\-\\*\\/]\\s*[0-9]+)+");

    private final String regex;
}
