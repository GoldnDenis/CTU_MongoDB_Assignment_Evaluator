package cz.cvut.fel.mongodb_assignment_evaluator.evaluation.model.query.type;

import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.model.enums.QueryTypes;
import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.model.enums.UpdateGroups;
import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.checker.bson.BsonDocumentChecker;
import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.model.modifier.QueryModifier;
import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.model.parameter.DocumentParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.model.parameter.QueryParameter;
import lombok.Getter;
import org.bson.BsonValue;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
public class UpdateQuery extends Query {
    public final static String UPDATE_ONE = "updateOne";
    public final static String UPDATE_MANY = "updateMany";

    private final DocumentParameter filter;
    private final List<DocumentParameter> updateDocuments;
    private final DocumentParameter options;

    public UpdateQuery(int lineNumber, int columnNumber, String comment, String query, QueryTypes type, String operator, List<QueryParameter> parameters, List<QueryModifier> modifiers, String collection, DocumentParameter filter, List<DocumentParameter> updateDocuments, DocumentParameter options) {
        super(lineNumber, columnNumber, comment, query, type, operator, collection, parameters, modifiers);
        this.filter = filter;
        this.updateDocuments = new ArrayList<>(updateDocuments);
        this.options = options;
    }

    public boolean isUpdateOne() {
        return operator.equalsIgnoreCase(UPDATE_ONE);
    }

    public boolean isUpdateMany() {
        return operator.equalsIgnoreCase(UPDATE_MANY);
    }

    public boolean containsOneOfUpdates(UpdateGroups updateGroups) {
        return updateDocuments.stream().anyMatch(d -> !BsonDocumentChecker.getAllRecursive(d.getDocument(), updateGroups.getOperations()).isEmpty());
    }

    public boolean upsertIsPositive() {
        Optional<BsonValue> optUpsertValue = BsonDocumentChecker.getRecursive(options.getDocument(), "upsert", 1);
        if (optUpsertValue.isPresent()) {
            BsonValue upsertValue = optUpsertValue.get();
            return (upsertValue.isBoolean() && upsertValue.asBoolean().getValue()) ||
                    (upsertValue.isInt32() && upsertValue.asInt32().getValue() == 1);
        }
        return false;
    }

    public boolean upsertIsNegative() {
        Optional<BsonValue> optUpsertValue = BsonDocumentChecker.getRecursive(options.getDocument(), "upsert", 1);
        if (optUpsertValue.isPresent()) {
            BsonValue upsertValue = optUpsertValue.get();
            return (upsertValue.isBoolean() && !upsertValue.asBoolean().getValue()) ||
                    (upsertValue.isInt32() && upsertValue.asInt32().getValue() == 0);
        }
        return false;
    }
}
