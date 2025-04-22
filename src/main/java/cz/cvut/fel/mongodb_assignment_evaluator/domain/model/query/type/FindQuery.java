package cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.enums.Operators;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.enums.QuerySelectors;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.modifier.QueryModifier;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.parameter.QueryParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.criteria.bson.BsonDocumentChecker;
import lombok.Getter;
import org.bson.BsonDocument;
import org.bson.BsonValue;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@Getter
public class FindQuery extends Query {
    private final BsonDocument filter;
    private final BsonDocument projection;
    private final BsonDocument options;

    public FindQuery(int lineNumber, int columnNumber, String comment, String query, Operators type, String operator,
                     List<QueryParameter> parameters, List<QueryModifier> modifiers, String collection,
                     BsonDocument filter, BsonDocument projection, BsonDocument options) {
        super(lineNumber, columnNumber, comment, query, type, operator, collection, parameters, modifiers);
        this.filter = filter;
        this.projection = projection;
        this.options = options;
    }

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

    public boolean containsProjection(boolean needPositive) {
        if (projection.isEmpty()) {
            return false;
        }
        Stream<BsonValue> fieldStream = projection.entrySet().stream()
                .filter(e -> !e.getKey().equalsIgnoreCase("_id"))
                .map(Map.Entry::getValue);
        if (needPositive) {
            return fieldStream.allMatch(e ->
                    (e.isBoolean() && e.asBoolean().getValue()) ||
                            (e.isNumber() && e.asInt32().getValue() == 1)
            );
        }
        return fieldStream.allMatch(e ->
                (e.isBoolean() && !e.asBoolean().getValue()) ||
                        (e.isNumber() && e.asInt32().getValue() == 0)
        );
    }

    @Override
    public boolean isTrivial() {
        return filter.isEmpty();
    }
}
