package cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter;

import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.bson.BsonChecker;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter.visitor.QueryParameterVisitor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bson.BsonDocument;
import org.bson.BsonValue;

import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;

@AllArgsConstructor
@Getter
public class DocumentParameter implements QueryParameter {
    private BsonDocument document;
    private int depth;

    public DocumentParameter() {
        document = new BsonDocument();
        depth = 0;
    }

    @Override
    public void accept(QueryParameterVisitor visitor) {
        visitor.visitDocumentParameter(this);
    }

    public BsonValue getValue(String field) {
        return BsonChecker.getValue(
                document,
                depth,
                field
        );
    }

    public BsonValue getValue(String field, int level) {
        return BsonChecker.getValue(
                document,
                level,
                field
        );
    }

    public boolean containsField(String field) {
        return getValue(field) != null;
    }

    public boolean containsField(String field, int level) {
        return getValue(field, level) != null;
    }

    public List<BsonValue> getValues(Collection<String> fields) {
        return fields.stream()
                .map(field -> BsonChecker.getValue(document, depth, field))
                .toList();
    }

    public List<BsonValue> getValues(Collection<String> fields, int level) {
        return fields.stream()
                .map(field -> BsonChecker.getValue(document, level, field))
                .toList();
    }

    /**
     * Checks if document contains at least one of the fields
     * @param fields
     * @return
     */
    public boolean containsField(Collection<String> fields) {
        return !getValues(fields).isEmpty();
    }

    public boolean containsField(Collection<String> fields, int level) {
        return !getValues(fields).isEmpty();
    }

    public String findFieldValueOfType(Class<?> clazz) {
        return BsonChecker.findFieldValueOfType(
                document,
                depth,
                clazz
        );
    }

    public String findFieldValueOfType(Class<?> clazz, int level) {
        return BsonChecker.findFieldValueOfType(
                document,
                level,
                clazz
        );
    }

    public boolean containsFieldValueOfType(Class<?> clazz) {
        return !findFieldValueOfType(clazz).isBlank();
    }

    public boolean containsFieldValueOfType(Class<?> clazz, int level) {
        return !findFieldValueOfType(clazz, level).isBlank();
    }

    public String findKeyMatchesPattern(Pattern pattern) {
        return BsonChecker.findKeyMatchesPattern(
                document,
                depth,
                pattern
        );
    }

    public String findKeyMatchesPattern(Pattern pattern, int level) {
        return BsonChecker.findKeyMatchesPattern(
                document,
                level,
                pattern
        );
    }

    public boolean containsKeyMatchesPattern(Pattern pattern) {
        return !findKeyMatchesPattern(pattern).isBlank();
    }

    public boolean containsKeyMatchesPattern(Pattern pattern, int level) {
        return !findKeyMatchesPattern(pattern, level).isBlank();
    }

    @Override
    public boolean isTrivial() {
        return document.isEmpty();
    }
}
