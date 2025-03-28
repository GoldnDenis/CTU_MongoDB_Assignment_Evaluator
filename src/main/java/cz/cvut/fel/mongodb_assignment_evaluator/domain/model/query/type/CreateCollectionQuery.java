package cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.enums.QueryTypes;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.modifier.QueryModifier;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.parameter.QueryParameter;
import lombok.Getter;
import org.bson.BsonDocument;

import java.util.List;


@Getter
public class CreateCollectionQuery extends Query {
    private final String collectionName;
    private final BsonDocument options;

    public CreateCollectionQuery(int lineNumber, int columnNumber, String comment, String query, QueryTypes type,
                                 String operator, String collection, List<QueryParameter> parameters, List<QueryModifier> modifiers,
                                 String collectionName, BsonDocument options) {
        super(lineNumber, columnNumber, comment, query, type, operator, collection, parameters, modifiers);
        this.collectionName = collectionName;
        this.options = options;
    }

    @Override
    public boolean isTrivial() {
        return collectionName == null || collectionName.isBlank();
    }
}
