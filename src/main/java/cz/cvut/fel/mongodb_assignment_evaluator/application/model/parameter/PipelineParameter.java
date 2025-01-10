package cz.cvut.fel.mongodb_assignment_evaluator.application.model.parameter;

import cz.cvut.fel.mongodb_assignment_evaluator.application.model.parameter.visitor.QueryParameterVisitor;
import lombok.Getter;
import org.bson.BsonDocument;

import java.util.ArrayList;
import java.util.List;

@Getter
public class PipelineParameter implements QueryParameter {
    private final List<BsonDocument> documentList;

    public PipelineParameter(List<BsonDocument> documentList) {
        this.documentList = new ArrayList<>(documentList);
    }

    @Override
    public void accept(QueryParameterVisitor visitor) {
        visitor.visitPipelineParameter(this);
    }

    @Override
    public boolean isTrivial() {
        return documentList.isEmpty() || !containsNonTrivialDocument();
    }

    public boolean containsNonTrivialDocument() {
        for (BsonDocument documentParameter : documentList) {
            if (!documentParameter.isEmpty()) {
                return true;
            }
        }
        return false;
    }
}
