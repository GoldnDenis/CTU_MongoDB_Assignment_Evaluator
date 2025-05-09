package cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.enums.Operators;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.modifier.QueryModifier;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.parameter.QueryParameter;
import lombok.Getter;
import org.bson.BsonDocument;

import java.util.List;

@Getter
public class ReplaceQueryToken extends QueryToken {
    private final BsonDocument filter;
    private final BsonDocument replacement;
    private final BsonDocument options;

    public ReplaceQueryToken(int lineNumber, int columnNumber, String comment, String query, Operators type, String operator,
                             List<QueryParameter> parameters, List<QueryModifier> modifiers, String collection,
                             BsonDocument filter, BsonDocument replacement, BsonDocument options) {
        super(lineNumber, columnNumber, comment, query, type, operator, collection, parameters, modifiers);
        this.filter = filter;
        this.replacement = replacement;
        this.options = options;
    }

    @Override
    public boolean isTrivial() {
        return replacement.isEmpty();
    }
}
