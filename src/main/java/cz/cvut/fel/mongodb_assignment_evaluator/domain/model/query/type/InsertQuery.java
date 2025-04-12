package cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.enums.Operators;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.enums.QueryTypes;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.modifier.QueryModifier;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.parameter.QueryParameter;
import lombok.Getter;
import org.bson.BsonDocument;

import java.util.ArrayList;
import java.util.List;

public class InsertQuery extends Query {
    private final static String INSERT_ONE = "insertOne";
    private final static String INSERT_MANY = "insertMany";

    @Getter
    private final List<BsonDocument> insertedDocuments;
    @Getter
    private final BsonDocument options;

    public InsertQuery(int lineNumber, int columnNumber, String comment, String query, Operators type, String operator,
                       List<QueryParameter> parameters, List<QueryModifier> modifiers, String collection,
                       List<BsonDocument> insertedDocuments, BsonDocument options) {
        super(lineNumber, columnNumber, comment, query, type, operator, collection, parameters, modifiers);
        this.insertedDocuments = new ArrayList<>(insertedDocuments);
        this.options = options;
    }

    public List<BsonDocument> getInsertedDocuments(boolean trivialIncluded) {
        List<BsonDocument> returnDocumentList = new ArrayList<>(insertedDocuments);
        if (!trivialIncluded) {
            returnDocumentList = returnDocumentList.stream()
                    .filter(doc -> !doc.isEmpty())
                    .toList();
        }
        return returnDocumentList;
    }

    @Override
    public boolean isTrivial() {
        return insertedDocuments.stream()
                .allMatch(BsonDocument::isEmpty);
    }

    @Override
    public int getQueryResultCount() {
        return getInsertedDocuments(false).size();
    }
}
