package cz.cvut.fel.mongodb_assignment_evaluator.service.model.query.type;

import cz.cvut.fel.mongodb_assignment_evaluator.service.enums.QueryTypes;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.modifier.QueryModifier;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.DocumentParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.QueryParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.query.type.visitor.QueryVisitor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
public class UpdateQuery extends Query {
    public final static String UPDATE_ONE = "updateOne";
    public final static String UPDATE_MANY = "updateMany";

    public final static Set<String> UPDATE_OPERATOR_SET = Set.of(
            "$set", "$rename", "$mul", "$inc",
            "$min", "$max", "$currentDate"
    );
    public final static Set<String> FORMULA_OPERATOR_SET = Set.of(
            "$mul", "$inc"
    );
    public final static Set<String> ADD_OPERATOR_SET = Set.of(
            "$set", "$mul", "$inc"
    );
    public final static Set<String> REMOVE_OPERATOR_SET = Set.of(
            "$unset"
    );
    public final static Set<String> ARRAY_UPDATE_OPERATOR_SET = Set.of(
            "$push"
    );
    public final static Set<String> ARRAY_REMOVE_OPERATOR_SET = Set.of(
            "$pull", "$pop"
    );

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
        return query.equalsIgnoreCase(UPDATE_ONE);
    }

    public boolean isUpdateMany() {
        return query.equalsIgnoreCase(UPDATE_MANY);
    }

    public boolean containsUpdateOperator() {
        return updateDocuments.stream()
                .anyMatch(d -> d.containsField(UPDATE_OPERATOR_SET));
    }

    public boolean containsFormulaOperator() {
        return updateDocuments.stream()
                .anyMatch(d -> d.containsField(FORMULA_OPERATOR_SET));
    }

    public boolean containsAddOperator() {
        return updateDocuments.stream()
                .anyMatch(d -> d.containsField(ADD_OPERATOR_SET));
    }

    public boolean containsRemoveOperator() {
        return updateDocuments.stream()
                .anyMatch(d -> d.containsField(REMOVE_OPERATOR_SET));
    }

    public boolean containsArrayUpdateOperator() {
        return updateDocuments.stream()
                .anyMatch(d -> d.containsField(ARRAY_UPDATE_OPERATOR_SET));
    }

    public boolean containsArrayRemoveOperator() {
        return updateDocuments.stream()
                .anyMatch(d -> d.containsField(ARRAY_REMOVE_OPERATOR_SET));
    }

    @Override
    public void accept(QueryVisitor visitor) {
        visitor.visitUpdateQuery(this);
    }
}
