package cz.cvut.fel.mongodb_assignment_evaluator.service.model.query.type;

import cz.cvut.fel.mongodb_assignment_evaluator.service.enums.QueryTypes;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.modifier.QueryModifier;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.DocumentParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.QueryParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.StringParameter;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.query.type.visitor.QueryVisitor;
import lombok.Getter;

import java.util.List;

@Getter
public class CreateCollectionQuery extends Query {
    private final StringParameter collectionName;
    private final DocumentParameter options;

    public CreateCollectionQuery(int lineNumber, int columnNumber, String comment, String query, QueryTypes type, String operator, String collection, List<QueryParameter> parameters, List<QueryModifier> modifiers, StringParameter collectionName, DocumentParameter options) {
        super(lineNumber, columnNumber, comment, query, type, operator, collection, parameters, modifiers);
        this.collectionName = collectionName;
        this.options = options;
    }

    @Override
    public void accept(QueryVisitor visitor) {
        visitor.visitCreateCollection(this);
    }
}
