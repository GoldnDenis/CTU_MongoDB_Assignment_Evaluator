package cz.cvut.fel.mongodb_assignment_evaluator.service.model.query.type;

import cz.cvut.fel.mongodb_assignment_evaluator.service.enums.QuerySelectors;
import cz.cvut.fel.mongodb_assignment_evaluator.service.enums.QueryTypes;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.bson.BsonDocumentChecker;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.modifier.QueryModifier;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.DocumentParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.QueryParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.query.type.visitor.QueryVisitor;
import lombok.Getter;
import org.bson.BsonValue;

import java.util.List;
import java.util.Map;
import java.util.Optional;

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

    public boolean filterContainsImplicitEquality() {
        return BsonDocumentChecker.containsImplicitEquality(filter.getDocument());
    }

    public boolean filterContainsSelector(QuerySelectors selector) {
        return filterContainsSelector(selector, BsonDocumentChecker.getDepth(filter.getDocument()));
    }

    public boolean filterContainsSelector(QuerySelectors selector, int level) {
//        Optional<BsonValue> foundValue = BsonDocumentChecker.getRecursive(filter.getDocument(), selector.getValue(), level);
//        return foundValue.filter(value -> checkFoundFilterValue(value, selector)).isPresent();
        return BsonDocumentChecker.getRecursive(filter.getDocument(), selector.getValue(), level).isPresent();
    }

//    private boolean checkFoundFilterValue(BsonValue foundValue, QuerySelectors selector) {
//        if (foundValue == null) {
//            return false;
//        }
//        if (selector.isDocument()) {
//            if (foundValue.isDocument()) {
//                return !foundValue.asDocument().isEmpty();
//            }
//        } else {
//            if (foundValue.isArray()) {
//                return !foundValue.asArray().isEmpty();
//            }
//        }
//        return false;
//    }

    // todo
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

//    @Override
//    public void accept(QueryVisitor visitor) {
//        visitor.visitFindQuery(this);
//    }
}
