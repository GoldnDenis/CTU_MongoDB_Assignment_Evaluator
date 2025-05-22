package cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.grader.criteria.update;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.enums.RegularExpressions;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.enums.UpdateOperators;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.grader.bson.BsonDocumentChecker;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.evaluation.grader.criteria.CriterionEvaluator;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.entity.Criterion;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type.UpdateQuery;
import org.bson.BsonValue;

import java.util.Optional;
import java.util.regex.Pattern;

/**
 * Counts matches of nested-field patterns in update documents.
 */
public class UpdatePatternCriterionEvaluator extends CriterionEvaluator<UpdateQuery> {
    private final Pattern requiredPattern;
    private final UpdateOperators requiredGroup;

    public UpdatePatternCriterionEvaluator(Criterion criterion, RegularExpressions regex, UpdateOperators requiredOperators) {
        super(UpdateQuery.class, criterion);
        this.requiredPattern = Pattern.compile(regex.getRegex());
        this.requiredGroup = requiredOperators;
    }

    @Override
    protected void evaluate(UpdateQuery query) {
        currentScore = query.findUpdateGroup(requiredGroup, 1).stream()
                .filter(BsonValue::isDocument)
                .map(BsonValue::asDocument)
                .map(doc -> BsonDocumentChecker.findKeyMatchesPattern(doc, requiredPattern))
                .filter(Optional::isPresent)
                .toList().size();
    }
}
