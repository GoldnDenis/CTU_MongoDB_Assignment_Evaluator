package cz.cvut.fel.mongodb_assignment_evaluator.evaluation.model.query.type;

import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.model.enums.QueryTypes;
import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.model.modifier.QueryModifier;
import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.model.parameter.QueryParameter;
import lombok.Getter;

import java.util.List;

@Getter
public class UnknownQuery extends Query {
    public UnknownQuery(int lineNumber, int columnNumber, String comment, String query, QueryTypes type, String operator, String collection, List<QueryParameter> parameters, List<QueryModifier> modifiers) {
        super(lineNumber, columnNumber, comment, query, type, operator, collection, parameters, modifiers);
    }


//    @Override
//    public void accept(QueryVisitor visitor) {
//        visitor.visitUnknownQuery(this);
//    }
}
