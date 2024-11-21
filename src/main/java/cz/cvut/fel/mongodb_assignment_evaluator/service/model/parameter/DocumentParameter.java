package cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter;

import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.BsonChecker;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.visitor.QueryParameterVisitor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bson.Document;

@AllArgsConstructor
@Getter
//public class DocumentParameter implements QueryParameter<DocumentParameter> {
public class DocumentParameter implements QueryParameter {
    private Document document;
    private int depth;

//    @Override
//    public DocumentParameter get() {
//        return this;
//    }

    @Override
    public void accept(QueryParameterVisitor visitor) {
        visitor.visitDocumentParameter(this);
    }

    public boolean containsEmbeddedObject() {
        return !getFieldWithEmbeddedObject().isBlank();
    }

    public String getFieldWithEmbeddedObject() {
        return BsonChecker.findFieldWithEmbeddedObject(document);
    }


    public boolean containsArray() {
        return !getFieldWithArray().isBlank();
    }

    public String getFieldWithArray() {
        BsonChecker.findFieldWithArray(document);
    }

//    public boolean containsEmbeddedObject(Document document) {
//        return BsonChecker.containsEmbeddedObject(document);
//    }

    public boolean contains(String fieldName) {
        return BsonChecker.contains(document, fieldName);
    }

    public boolean firstLevelContains(String fieldName) {
        return document.containsKey(fieldName);
    }

    public boolean isTrivial() {
        return document.isEmpty();
    }
}
