
package cz.cvut.fel.task_evaluator.model.token;

import cz.cvut.fel.task_evaluator.enums.TokenTypes;

public class TokenBuilder {
    private String value;
    private TokenTypes type;
    private int lineNumber;
    private int columnNumber;

    public TokenBuilder() {
        reset();
    }

    public TokenBuilder setValue(String value) {
        this.value = value;
        return this;
    }

    public TokenBuilder setType(TokenTypes type) {
        this.type = type;
        return this;
    }

    public TokenBuilder setLineNumber(int rowIndex) {
        this.lineNumber = rowIndex;
        return this;
    }

    public TokenBuilder setColumnNumber(int columnIndex) {
        this.columnNumber = columnIndex;
        return this;
    }

    public void reset() {
        this.value = "";
        this.type = TokenTypes.UNKNOWN;
        this.lineNumber = -1;
        this.columnNumber = -1;
    }

    public Token build() {
        return new Token(value, type, lineNumber, columnNumber);
    }
}
