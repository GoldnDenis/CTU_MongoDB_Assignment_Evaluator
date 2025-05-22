package cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.enums.MongoCommands;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.enums.QuerySelectors;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.grader.bson.BsonDocumentChecker;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.modifier.QueryModifier;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.parameter.QueryParameter;
import lombok.Getter;
import org.bson.BsonDocument;
import org.bson.BsonValue;

import java.util.List;
import java.util.Map;

@Getter
public class FindQuery extends MongoQuery {
    private final BsonDocument filter;
    private final BsonDocument projection;
    private final BsonDocument options;

    public FindQuery(int lineNumber, int columnNumber, String precedingComment, String query, MongoCommands type, String operator,
                     List<QueryParameter> parameters, List<QueryModifier> modifiers, String collection, List<String> innerComments,
                     BsonDocument filter, BsonDocument projection, BsonDocument options) {
        super(lineNumber, columnNumber, precedingComment, query, type, operator, collection, parameters, modifiers, innerComments);
        this.filter = filter;
        this.projection = projection;
        this.options = options;
    }

    /**
     * Checke the filter document for selectors
     * @param selectors
     * @return if contains at least one
     */
    public boolean containsSelectors(QuerySelectors selectors) {
        for (String selector : selectors.getOperators()) {
            boolean found = false;
            if (selector.equalsIgnoreCase("$and") || selector.equalsIgnoreCase("$or")) {
                found = BsonDocumentChecker.getRecursive(filter, selector, 1).isPresent();
            } else {
                found = BsonDocumentChecker.getRecursive(filter, selector).isPresent();
            }
            if (found) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks the fields (except _id) of the projection document:
     * 1) if negative, then all should be negative (or false);
     * 2) if positive, then all should be positive (or true).
     * @param needPositive
     * @return whether it has the required projection type
     */
    public boolean containsProjection(boolean needPositive) {
        if (projection.isEmpty()) {
            return false;
        }
        List<BsonValue> projectionValues = projection.entrySet().stream()
                .filter(e -> !e.getKey().equalsIgnoreCase("_id"))
                .map(Map.Entry::getValue)
                .toList();
        if (projectionValues.isEmpty()) {
            return false;
        }
        if (needPositive) {
            return projectionValues.stream().allMatch(e ->
                    (e.isString() && e.asString().getValue().equalsIgnoreCase("true")) ||
                            (e.isNumber() && e.asInt32().getValue() == 1)
            );
        }
        return projectionValues.stream().allMatch(e ->
                (e.isString() && e.asString().getValue().equalsIgnoreCase("false")) ||
                        (e.isNumber() && e.asInt32().getValue() == 0)
        );
    }

    /**
     * @return if its filter is empty
     */
    @Override
    public boolean isTrivial() {
        return filter.isEmpty();
    }
}
