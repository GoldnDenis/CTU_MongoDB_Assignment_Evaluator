package cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.enums.MongoCommands;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.enums.UpdateOperators;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.grader.bson.BsonDocumentChecker;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.modifier.QueryModifier;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.parameter.QueryParameter;
import lombok.Getter;
import org.bson.BsonDocument;
import org.bson.BsonValue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Getter
public class UpdateQuery extends MongoQuery {
    private final BsonDocument filter;
    private final List<BsonDocument> updateDocuments;
    private final BsonDocument options;

    public UpdateQuery(int lineNumber, int columnNumber, String precedingComment, String query, MongoCommands type,
                       String operator, List<QueryParameter> parameters, List<QueryModifier> modifiers, String collection, List<String> innerComments,
                       BsonDocument filter, List<BsonDocument> updateDocuments, BsonDocument options) {
        super(lineNumber, columnNumber, precedingComment, query, type, operator, collection, parameters, modifiers, innerComments);
        this.filter = filter;
        this.updateDocuments = new ArrayList<>(updateDocuments);
        this.options = options;
    }

    /**
     * looks in update document/array for specified group=
     * @param group=
     * @return all found values of these operators
     */
    public List<BsonValue> findUpdateGroup(UpdateOperators group) {
        return updateDocuments.stream()
                .map(doc -> BsonDocumentChecker.getAll(doc, group.getOperations()))
                .flatMap(Collection::stream)
                .toList();
    }

    /**
     * looks in update document/array for specified group at specified depth
     * @param group
     * @param depth
     * @return all found values of these operators
     */
    public List<BsonValue> findUpdateGroup(UpdateOperators group, int depth) {
        return updateDocuments.stream()
                .map(doc -> BsonDocumentChecker.getAll(doc, group.getOperations(), depth))
                .flatMap(Collection::stream)
                .toList();
    }

    /**
     * @return if options contain upsert and is set on true
     */
    public boolean containsUpsert() {
        Optional<BsonValue> optUpsertValue = BsonDocumentChecker.getRecursive(options, "upsert", 1);
        if (optUpsertValue.isPresent()) {
            BsonValue upsertValue = optUpsertValue.get();
            return (upsertValue.isString() && upsertValue.asString().getValue().equalsIgnoreCase("true"));
        }
        return false;
    }

    /**
     * @return if update document/array is empty
     */
    @Override
    public boolean isTrivial() {
        return updateDocuments.isEmpty() ||
                updateDocuments.stream()
                        .allMatch(BsonDocument::isEmpty);
    }
}
