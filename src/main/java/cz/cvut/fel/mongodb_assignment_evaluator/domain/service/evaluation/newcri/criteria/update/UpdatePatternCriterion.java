package cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.newcri.criteria.update;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.enums.UpdateGroups;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type.UpdateQuery;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.checker.bson.BsonDocumentChecker;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.newcri.EvaluationCriterion;
import org.bson.BsonDocument;
import org.bson.BsonString;
import org.bson.BsonValue;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Pattern;

public class UpdatePatternCriterion extends EvaluationCriterion<UpdateQuery> {
    private final Pattern requiredPattern;

    public UpdatePatternCriterion(String regex) {
        super(UpdateQuery.class);
        this.requiredPattern = Pattern.compile(regex);
    }

    // todo debug - old logic
    @Override
    protected void evaluate(UpdateQuery query) {
        for (BsonDocument updateDocument : query.getUpdateDocuments()) {
            List<BsonValue> foundOperatorValues = BsonDocumentChecker.getAllRecursive(updateDocument, Set.of("$set", "$mul", "$inc"), 1);
            for (BsonValue operatorValue : foundOperatorValues) {
                if (!operatorValue.isDocument()) {
                    continue;
                }
                Optional<String> foundNestedKey = BsonDocumentChecker.findKeyMatchesPattern(operatorValue.asDocument(), requiredPattern);
                if (foundNestedKey.isPresent()) {
                    currentScore++;
                    return;
                }
            }
        }
    }
}
