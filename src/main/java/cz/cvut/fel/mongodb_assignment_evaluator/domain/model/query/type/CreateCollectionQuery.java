package cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.enums.MongoCommands;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.modifier.QueryModifier;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.parameter.QueryParameter;
import lombok.Getter;
import org.bson.BsonDocument;

import java.util.List;


@Getter
public class CreateCollectionQuery extends MongoQuery {
    private final String collectionName;
    private final BsonDocument options;

    public CreateCollectionQuery(int lineNumber, int columnNumber, String precedingComment, String query, MongoCommands type,
                                 String operator, String collection, List<QueryParameter> parameters, List<QueryModifier> modifiers, List<String> innerComments,
                                 String collectionName, BsonDocument options) {
        super(lineNumber, columnNumber, precedingComment, query, type, operator, collection, parameters, modifiers, innerComments);
        this.collectionName = collectionName;
        this.options = options;
    }

    /**
     * @return an empty create collection
     * Note this is unachievable, since it is a syntax error
     */
    @Override
    public boolean isTrivial() {
        return collectionName == null || collectionName.isBlank();
    }
}
