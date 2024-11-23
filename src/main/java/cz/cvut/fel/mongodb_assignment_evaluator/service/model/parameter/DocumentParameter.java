package cz.cvut.fel.mongodb_assignment_evaluator.service.model.parameter;

import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.BsonChecker;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.visitor.QueryParameterVisitor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bson.Document;

import java.util.regex.Pattern;

@AllArgsConstructor
@Getter
public class DocumentParameter implements QueryParameter {
    private Document document;
    private int depth;

    @Override
    public void accept(QueryParameterVisitor visitor) {
        visitor.visitDocumentParameter(this);
    }

    public Object getValue(String field) {
        return BsonChecker.getValue(
                document,
                depth,
                field
        );
    }

    public Object getValue(String field, int level) {
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

    public String findFieldMatchesPattern(Pattern pattern) {
        return BsonChecker.findFieldMatchesPattern(
                document,
                depth,
                pattern
        );
    }

    public String findFieldMatchesPattern(Pattern pattern, int level) {
        return BsonChecker.findFieldMatchesPattern(
                document,
                level,
                pattern
        );
    }

    public boolean containsFieldMatchesPattern(Pattern pattern) {
        return !findFieldMatchesPattern(pattern).isBlank();
    }

    public boolean containsFieldMatchesPattern(Pattern pattern, int level) {
        return !findFieldMatchesPattern(pattern, level).isBlank();
    }

    public boolean isTrivial() {
        return document.isEmpty();
    }
}
