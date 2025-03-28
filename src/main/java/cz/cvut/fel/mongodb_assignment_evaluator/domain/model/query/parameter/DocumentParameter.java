package cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.parameter;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.parameter.visitor.QueryParameterVisitor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bson.BsonDocument;

@AllArgsConstructor
@Getter
public class DocumentParameter implements QueryParameter {
    private BsonDocument document;
//    private int depth;

    public DocumentParameter() {
        document = new BsonDocument();
//        depth = 0;
    }

    @Override
    public void accept(QueryParameterVisitor visitor) {
        visitor.visitDocumentParameter(this);
    }

    @Override
    public boolean isTrivial() {
        return document.isEmpty();
    }
}
