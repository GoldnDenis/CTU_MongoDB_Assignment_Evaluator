package cz.cvut.fel.mongodb_assignment_evaluator.evaluation.model.query.type;

import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.model.enums.QuerySelectors;
import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.model.enums.QueryTypes;
import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.checker.bson.BsonDocumentChecker;
import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.model.modifier.QueryModifier;
import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.model.parameter.DocumentParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.model.parameter.QueryParameter;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
public class FindQuery extends Query {
    private final DocumentParameter filter;
    private final DocumentParameter projection;
    private final DocumentParameter options;

    public FindQuery(int lineNumber, int columnNumber, String comment, String query, QueryTypes type, String operator, List<QueryParameter> parameters, List<QueryModifier> modifiers, String collection, DocumentParameter filter, DocumentParameter projection, DocumentParameter options) {
        super(lineNumber, columnNumber, comment, query, type, operator, collection, parameters, modifiers);
        this.filter = filter;
        this.projection = projection;
        this.options = options;
    }

    public boolean filterContainsSelector(QuerySelectors selector) {
        return filterContainsSelector(selector, BsonDocumentChecker.getDepth(filter.getDocument()));
    }

    public boolean filterContainsSelector(QuerySelectors selector, int level) {
        return BsonDocumentChecker.getRecursive(filter.getDocument(), selector.getValue(), level).isPresent();
    }

    public boolean projectionIsPositive() {
        if (projection.isTrivial()) {
            return false;
        }
        return projection.getDocument().entrySet().stream()
                .filter(e -> !e.getKey().equalsIgnoreCase("_id"))
                .map(Map.Entry::getValue)
                .allMatch(e ->
                        (e.isBoolean() && e.asBoolean().getValue()) ||
                                (e.isNumber() && e.asInt32().getValue() == 1)
                );
    }

    public boolean projectionIsNegative() {
        if (projection.isTrivial()) {
            return false;
        }
        return projection.getDocument().entrySet().stream()
                .filter(e -> !e.getKey().equalsIgnoreCase("_id"))
                .map(Map.Entry::getValue)
                .allMatch(e ->
                        (e.isBoolean() && !e.asBoolean().getValue()) ||
                                (e.isNumber() && e.asInt32().getValue() == 0)
                );
    }
}
