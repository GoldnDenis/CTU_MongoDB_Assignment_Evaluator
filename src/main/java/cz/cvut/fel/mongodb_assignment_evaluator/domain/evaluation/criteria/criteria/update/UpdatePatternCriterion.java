package cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.criteria.criteria.update;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.enums.RegularExpressions;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.enums.UpdateGroups;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.criteria.bson.BsonDocumentChecker;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.criteria.criteria.EvaluationCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type.UpdateQueryToken;
import org.bson.BsonValue;

import java.util.Optional;
import java.util.regex.Pattern;

public class UpdatePatternCriterion extends EvaluationCriterion<UpdateQueryToken> {
    private final Pattern requiredPattern;
    private final UpdateGroups requiredGroup;

    public UpdatePatternCriterion(int priority, RegularExpressions regex, UpdateGroups requiredGroup) {
        super(UpdateQueryToken.class, priority);
        this.requiredPattern = Pattern.compile(regex.getRegex());
        this.requiredGroup = requiredGroup;
    }

    @Override
    protected void evaluate(UpdateQueryToken query) {
        currentScore = query.findUpdateGroup(requiredGroup, 1).stream()
                .filter(BsonValue::isDocument)
                .map(BsonValue::asDocument)
                .map(doc -> BsonDocumentChecker.findKeyMatchesPattern(doc, requiredPattern))
                .filter(Optional::isPresent)
                .count();
    }
}
