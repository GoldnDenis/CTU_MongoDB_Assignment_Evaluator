package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.update.nested;

import cz.cvut.fel.mongodb_assignment_evaluator.service.enums.Criteria;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.BsonChecker;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.MockMongoDB;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.update.UpdateCriterion;
import org.bson.BsonDocument;

import java.util.*;
import java.util.regex.Pattern;

//todo update
public class UpdateNestedDocumentCriterion extends UpdateCriterion {
    private final Pattern nestedPattern = Pattern.compile("^[a-zA-Z]+[a-zA-Z0-9_]*(\\.(\\$(\\[[a-zA-Z0-9]*\\])?|[0-9]+))*(\\.[a-zA-Z]+[a-zA-Z0-9_]*)+");

    public UpdateNestedDocumentCriterion(MockMongoDB mockDb) {
        super(
                mockDb,
                Criteria.UPDATE_NESTED_DOCUMENT.getDescription(),
                Criteria.UPDATE_NESTED_DOCUMENT.getRequiredCount(),
                List.of("$set", "$mul", "$inc"),
                true
        );
    }

    @Override
    protected Set<String> getFieldsSet(BsonDocument document) {
        String key = BsonChecker.findKeyMatchesPattern(
                document,
                1,
                nestedPattern
        );
        if (key.isBlank()) {
            return Collections.emptySet();
        }
        return Set.of(key);
    }
}
