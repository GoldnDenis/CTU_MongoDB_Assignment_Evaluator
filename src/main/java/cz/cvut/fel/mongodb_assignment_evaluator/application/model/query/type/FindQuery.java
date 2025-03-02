package cz.cvut.fel.mongodb_assignment_evaluator.application.model.query.type;

import cz.cvut.fel.mongodb_assignment_evaluator.enums.QuerySelectors;
import cz.cvut.fel.mongodb_assignment_evaluator.enums.QueryTypes;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.bson.BsonDocumentChecker;
import cz.cvut.fel.mongodb_assignment_evaluator.application.model.query.modifier.QueryModifier;
import cz.cvut.fel.mongodb_assignment_evaluator.application.model.query.parameter.QueryParameter;
import lombok.Getter;
import org.bson.BsonDocument;

import java.util.List;
import java.util.Map;

@Getter
public class FindQuery extends Query {
    private final BsonDocument filter;
    private final BsonDocument projection;
    private final BsonDocument options;

    public FindQuery(int lineNumber, int columnNumber, String comment, String query, QueryTypes type, String operator,
                     List<QueryParameter> parameters, List<QueryModifier> modifiers, String collection,
                     BsonDocument filter, BsonDocument projection, BsonDocument options) {
        super(lineNumber, columnNumber, comment, query, type, operator, collection, parameters, modifiers);
        this.filter = filter;
        this.projection = projection;
        this.options = options;
    }

    public boolean filterContainsSelector(QuerySelectors selector) {
        return filterContainsSelector(selector, BsonDocumentChecker.getDepth(filter));
    }

    public boolean filterContainsSelector(QuerySelectors selector, int level) {
        return BsonDocumentChecker.getRecursive(filter, selector.getValue(), level).isPresent();
    }

    public boolean projectionIsPositive() {
        if (projection.isEmpty()) {
            return false;
        }
        return projection.entrySet().stream()
                .filter(e -> !e.getKey().equalsIgnoreCase("_id"))
                .map(Map.Entry::getValue)
                .allMatch(e ->
                        (e.isBoolean() && e.asBoolean().getValue()) ||
                                (e.isNumber() && e.asInt32().getValue() == 1)
                );
    }

    public boolean projectionIsNegative() {
        if (projection.isEmpty()) {
            return false;
        }
        return projection.entrySet().stream()
                .filter(e -> !e.getKey().equalsIgnoreCase("_id"))
                .map(Map.Entry::getValue)
                .allMatch(e ->
                        (e.isBoolean() && !e.asBoolean().getValue()) ||
                                (e.isNumber() && e.asInt32().getValue() == 0)
                );
    }
}
