package cz.cvut.fel.mongodb_assignment_evaluator.evaluation.model.query.type;

import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.model.enums.QueryTypes;
import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.model.modifier.QueryModifier;
import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.model.parameter.DocumentParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.model.parameter.QueryParameter;
import lombok.Getter;

import java.util.List;

@Getter
public class ReplaceOneQuery extends Query {
//    private final String collection;
    private final DocumentParameter filter;
    private final DocumentParameter replacement;
    private final DocumentParameter options;

    public ReplaceOneQuery(int lineNumber, int columnNumber, String comment, String query, QueryTypes type, String operator, List<QueryParameter> parameters, List<QueryModifier> modifiers, String collection, DocumentParameter filter, DocumentParameter replacement, DocumentParameter options) {
        super(lineNumber, columnNumber, comment, query, type, operator, collection, parameters, modifiers);
//        this.collection = collection;
        this.filter = filter;
        this.replacement = replacement;
        this.options = options;
    }

//    @Override
//    public void accept(QueryVisitor visitor) {
//        visitor.visitReplaceOneQuery(this);
//    }
}