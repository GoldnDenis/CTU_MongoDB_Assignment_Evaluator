package cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.enums.Operators;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.enums.UpdateGroups;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.modifier.QueryModifier;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.parameter.QueryParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.criteria.bson.BsonDocumentChecker;
import lombok.Getter;
import org.bson.BsonDocument;
import org.bson.BsonValue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Getter
public class UpdateQuery extends Query {
    private final BsonDocument filter;
    private final List<BsonDocument> updateDocuments;
    private final BsonDocument options;

    public UpdateQuery(int lineNumber, int columnNumber, String comment, String query, Operators type,
                       String operator, List<QueryParameter> parameters, List<QueryModifier> modifiers, String collection,
                       BsonDocument filter, List<BsonDocument> updateDocuments, BsonDocument options) {
        super(lineNumber, columnNumber, comment, query, type, operator, collection, parameters, modifiers);
        this.filter = filter;
        this.updateDocuments = new ArrayList<>(updateDocuments);
        this.options = options;
    }

    public List<BsonValue> findUpdateGroup(UpdateGroups group) {
        return updateDocuments.stream()
                .map(doc -> BsonDocumentChecker.getAll(doc, group.getOperations()))
                .flatMap(Collection::stream)
                .toList();
    }

    public List<BsonValue> findUpdateGroup(UpdateGroups group, int depth) {
        return updateDocuments.stream()
                .map(doc -> BsonDocumentChecker.getAll(doc, group.getOperations(), depth))
                .flatMap(Collection::stream)
                .toList();
    }

    public boolean containsUpsert(boolean needPositive) {
        Optional<BsonValue> optUpsertValue = BsonDocumentChecker.getRecursive(options, "upsert", 1);
        if (optUpsertValue.isPresent()) {
            BsonValue upsertValue = optUpsertValue.get();
            if (needPositive) {
                return (upsertValue.isBoolean() && upsertValue.asBoolean().getValue()) ||
                        (upsertValue.isInt32() && upsertValue.asInt32().getValue() == 1);
            }
            return (upsertValue.isBoolean() && !upsertValue.asBoolean().getValue()) ||
                    (upsertValue.isInt32() && upsertValue.asInt32().getValue() == 0);
        }
        return false;
    }

    // todo revisit the conditions for triviality
    @Override
    public boolean isTrivial() {
        return updateDocuments.isEmpty() ||
                updateDocuments.stream()
                        .allMatch(BsonDocument::isEmpty);
    }
}
