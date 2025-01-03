package cz.cvut.fel.mongodb_assignment_evaluator.evaluation.model.query.type;

import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.model.enums.QueryTypes;
import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.model.modifier.QueryModifier;
import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.model.parameter.DocumentParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.model.parameter.QueryParameter;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class InsertQuery extends Query {
    private final static String INSERT_ONE = "insertOne";
    private final static String INSERT_MANY = "insertMany";

    private final List<DocumentParameter> insertedDocuments;
    private final DocumentParameter options;

    public InsertQuery(int lineNumber, int columnNumber, String comment, String query, QueryTypes type, String operator, List<QueryParameter> parameters, List<QueryModifier> modifiers, String collection, List<DocumentParameter> insertedDocuments, DocumentParameter options) {
        super(lineNumber, columnNumber, comment, query, type, operator, collection, parameters, modifiers);
        this.insertedDocuments = new ArrayList<>(insertedDocuments);
        this.options = options;
    }

    public List<DocumentParameter> getNonTrivialInsertedDocuments() {
        return getInsertedDocuments().stream()
                .filter(d -> !d.isTrivial())
                .toList();
    }

    public boolean isInsertOne() {
        return operator.equalsIgnoreCase(INSERT_ONE);
    }

    public boolean isInsertMany() {
        return operator.equalsIgnoreCase(INSERT_MANY);
    }
}
