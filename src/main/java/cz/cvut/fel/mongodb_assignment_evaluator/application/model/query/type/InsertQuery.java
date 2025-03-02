package cz.cvut.fel.mongodb_assignment_evaluator.application.model.query.type;

import cz.cvut.fel.mongodb_assignment_evaluator.enums.QueryTypes;
import cz.cvut.fel.mongodb_assignment_evaluator.application.model.query.modifier.QueryModifier;
import cz.cvut.fel.mongodb_assignment_evaluator.application.model.query.parameter.QueryParameter;
import lombok.Getter;
import org.bson.BsonDocument;

import java.util.ArrayList;
import java.util.List;

@Getter
public class InsertQuery extends Query {
    private final static String INSERT_ONE = "insertOne";
    private final static String INSERT_MANY = "insertMany";

    private final List<BsonDocument> insertedDocuments;
    private final BsonDocument options;

    public InsertQuery(int lineNumber, int columnNumber, String comment, String query, QueryTypes type, String operator,
                       List<QueryParameter> parameters, List<QueryModifier> modifiers, String collection,
                       List<BsonDocument> insertedDocuments, BsonDocument options) {
        super(lineNumber, columnNumber, comment, query, type, operator, collection, parameters, modifiers);
        this.insertedDocuments = new ArrayList<>(insertedDocuments);
        this.options = options;
    }

    public List<BsonDocument> getNonTrivialInsertedDocuments() {
        return getInsertedDocuments().stream()
                .filter(d -> !d.isEmpty())
                .toList();
    }

    public boolean isInsertOne() {
        return operator.equalsIgnoreCase(INSERT_ONE);
    }

    public boolean isInsertMany() {
        return operator.equalsIgnoreCase(INSERT_MANY);
    }
}
