package cz.cvut.fel.mongodb_assignment_evaluator.service.model.query.type;

import cz.cvut.fel.mongodb_assignment_evaluator.service.enums.QuerySelectors;
import cz.cvut.fel.mongodb_assignment_evaluator.service.enums.QueryTypes;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.modifier.QueryModifier;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.DocumentParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.QueryParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.query.type.visitor.QueryVisitor;
import lombok.Getter;
import org.bson.BsonValue;

import java.util.List;
import java.util.Map;

@Getter
public class FindQuery extends Query {
//    private final String collection;
    private final DocumentParameter filter;
    private final DocumentParameter projection;
    private final DocumentParameter options;

    public FindQuery(int lineNumber, int columnNumber, String comment, String query, QueryTypes type, String operator, List<QueryParameter> parameters, List<QueryModifier> modifiers, String collection, DocumentParameter filter, DocumentParameter projection, DocumentParameter options) {
        super(lineNumber, columnNumber, comment, query, type, operator, collection, parameters, modifiers);
//        this.collection = collection;
        this.filter = filter;
        this.projection = projection;
        this.options = options;
    }

    // todo should it be on different levels or it's always fixated
    public boolean filterContainsSelector(QuerySelectors selector) {
        BsonValue foundValue = filter.getValue(selector.getValue());
        return checkFoundFilterValue(foundValue, selector);
    }

    public boolean filterContainsSelector(QuerySelectors selector, int level) {
        BsonValue foundValue = filter.getValue(selector.getValue(), level);
        return checkFoundFilterValue(foundValue, selector);
    }

    private boolean checkFoundFilterValue(BsonValue foundValue, QuerySelectors selector) {
        if (foundValue == null) {
            return false;
        }
        if (selector.isDocument()) {
            if (foundValue.isDocument()) {
                return !foundValue.asDocument().isEmpty();
            }
        } else {
            if (foundValue.isArray()) {
                return !foundValue.asArray().isEmpty();
            }
        }
        return false;
    }

    public boolean projectionIsPositive() {
        return projection.getDocument().entrySet().stream()
                .filter(e -> !e.getKey().equalsIgnoreCase("_id"))
                .map(Map.Entry::getValue)
                .allMatch(e ->
                        (e.isBoolean() && e.asBoolean().getValue()) ||
                                (e.isNumber() && e.asInt32().getValue() == 1)
                );
    }

    public boolean projectionIsNegative() {
        return projection.getDocument().entrySet().stream()
                .filter(e -> !e.getKey().equalsIgnoreCase("_id"))
                .map(Map.Entry::getValue)
                .allMatch(e ->
                        (e.isBoolean() && !e.asBoolean().getValue()) ||
                                (e.isNumber() && e.asInt32().getValue() == 0)
                );
    }

    @Override
    public void accept(QueryVisitor visitor) {
        visitor.visitFindQuery(this);
    }
}
