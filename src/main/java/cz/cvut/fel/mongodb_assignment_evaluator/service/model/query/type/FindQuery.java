package cz.cvut.fel.mongodb_assignment_evaluator.service.model.query.type;

import cz.cvut.fel.mongodb_assignment_evaluator.service.enums.QueryTypes;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.modifier.QueryModifier;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.DocumentParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.QueryParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.query.type.visitor.QueryVisitor;
import lombok.Getter;

import java.util.List;

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

    @Override
    public void accept(QueryVisitor visitor) {
        visitor.visitFindQuery(this);
    }
}
