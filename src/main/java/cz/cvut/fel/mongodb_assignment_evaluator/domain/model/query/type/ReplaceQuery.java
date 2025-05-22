package cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.enums.MongoCommands;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.modifier.QueryModifier;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.parameter.QueryParameter;
import lombok.Getter;
import org.bson.BsonDocument;

import java.util.List;

@Getter
public class ReplaceQuery extends MongoQuery {
    private final BsonDocument filter;
    private final BsonDocument replacement;
    private final BsonDocument options;

    public ReplaceQuery(int lineNumber, int columnNumber, String precedingComment, String query, MongoCommands type, String operator,
                        List<QueryParameter> parameters, List<QueryModifier> modifiers, String collection, List<String> innerComments,
                        BsonDocument filter, BsonDocument replacement, BsonDocument options) {
        super(lineNumber, columnNumber, precedingComment, query, type, operator, collection, parameters, modifiers, innerComments);
        this.filter = filter;
        this.replacement = replacement;
        this.options = options;
    }

    /**
     * @return if replacement document is empty
     */
    @Override
    public boolean isTrivial() {
        return replacement.isEmpty();
    }
}
