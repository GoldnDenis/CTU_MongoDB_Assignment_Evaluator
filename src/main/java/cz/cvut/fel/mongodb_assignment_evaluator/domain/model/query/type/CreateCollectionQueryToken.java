package cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.enums.Operators;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.modifier.QueryModifier;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.parameter.QueryParameter;
import lombok.Getter;
import org.bson.BsonDocument;

import java.util.List;


@Getter
public class CreateCollectionQueryToken extends QueryToken {
    private final String collectionName;
    private final BsonDocument options;

    public CreateCollectionQueryToken(int lineNumber, int columnNumber, String precedingComment, String query, Operators type,
                                      String operator, String collection, List<QueryParameter> parameters, List<QueryModifier> modifiers, List<String> innerComments,
                                      String collectionName, BsonDocument options) {
        super(lineNumber, columnNumber, precedingComment, query, type, operator, collection, parameters, modifiers, innerComments);
        this.collectionName = collectionName;
        this.options = options;
    }

    @Override
    public boolean isTrivial() {
        return collectionName == null || collectionName.isBlank();
    }
}
