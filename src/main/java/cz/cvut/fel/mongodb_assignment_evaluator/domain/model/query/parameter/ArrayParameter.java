package cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.parameter;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.parameter.visitor.QueryParameterVisitor;
import lombok.Getter;
import org.bson.BsonDocument;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents pipelines in the MongoDB
 */
@Getter
public class ArrayParameter implements QueryParameter {
    private final List<BsonDocument> documentList;

    public ArrayParameter(List<BsonDocument> documentList) {
        this.documentList = new ArrayList<>(documentList);
    }

    @Override
    public void accept(QueryParameterVisitor visitor) {
        visitor.visitArrayParameter(this);
    }
}
