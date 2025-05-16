package cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.enums.Operators;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.modifier.QueryModifier;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.parameter.QueryParameter;
import lombok.Getter;
import org.bson.BsonDocument;

import java.util.ArrayList;
import java.util.List;

@Getter
public class InsertQueryToken extends QueryToken {
    private final List<BsonDocument> insertedDocuments;
    private final BsonDocument options;

    public InsertQueryToken(int lineNumber, int columnNumber, String precedingComment, String query, Operators type, String operator,
                            List<QueryParameter> parameters, List<QueryModifier> modifiers, String collection, List<String> innerComments,
                            List<BsonDocument> insertedDocuments, BsonDocument options) {
        super(lineNumber, columnNumber, precedingComment, query, type, operator, collection, parameters, modifiers, innerComments);
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
