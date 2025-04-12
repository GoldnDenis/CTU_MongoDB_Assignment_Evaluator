package cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.criteria.criteria.update;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.enums.RegularExpressions;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.enums.UpdateGroups;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type.UpdateQuery;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.criteria.bson.BsonDocumentChecker;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.criteria.EvaluationCriterion;
import org.bson.BsonValue;

import java.util.Optional;
import java.util.regex.Pattern;

public class UpdatePatternCriterion extends EvaluationCriterion<UpdateQuery> {
    private final Pattern requiredPattern;
    private final UpdateGroups requiredGroup;

    public UpdatePatternCriterion(int priority, RegularExpressions regex, UpdateGroups requiredGroup) {
        super(UpdateQuery.class, priority);
        this.requiredPattern = Pattern.compile(regex.getRegex());
        this.requiredGroup = requiredGroup;
    }

    // todo Evaluation ambiguity: a) once per query /or/ b) all occurrences
    @Override
    protected void evaluate(UpdateQuery query) {
        currentScore = query.findUpdateGroup(requiredGroup, 1).stream()
                .filter(BsonValue::isDocument)
                .map(BsonValue::asDocument)
                .map(doc -> BsonDocumentChecker.findKeyMatchesPattern(doc, requiredPattern))
                .filter(Optional::isPresent)
                .count();

//        for (BsonDocument updateDocument : query.getUpdateDocuments()) {
//            List<BsonValue> foundOperatorValues = BsonDocumentChecker.getAll(updateDocument, Set.of("$set", "$mul", "$inc"), 1);
//            for (BsonValue operatorValue : foundOperatorValues) {
//                if (!operatorValue.isDocument()) {
//                    continue;
//                }
//                Optional<String> foundNestedKey = BsonDocumentChecker.findKeyMatchesPattern(operatorValue.asDocument(), requiredPattern);
//                if (foundNestedKey.isPresent()) {
//                    currentScore++;
//                    return;
//                }
//            }
//        }
    }
}
