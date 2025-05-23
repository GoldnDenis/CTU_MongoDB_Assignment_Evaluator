package cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.parameter;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.parameter.visitor.QueryParameterVisitor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bson.BsonDocument;

/**
 * Represent JSON-like document in MongoDB
 */
@AllArgsConstructor
@Getter
public class DocumentParameter implements QueryParameter {
    private BsonDocument document;

    public DocumentParameter() {
        document = new BsonDocument();
    }

    @Override
    public void accept(QueryParameterVisitor visitor) {
        visitor.visitDocumentParameter(this);
    }
}
