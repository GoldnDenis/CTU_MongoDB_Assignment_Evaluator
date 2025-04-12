package cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.criteria.criteria.find;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type.FindQuery;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.criteria.EvaluationCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.criteria.bson.BsonDocumentChecker;
import org.bson.BsonType;

public class FindTypeCriterion extends EvaluationCriterion<FindQuery> {
//    private final Pattern requiredPattern;
    private final BsonType requiredType;

//    public FindTypeCriterion(int priority, RegularExpressions regex) {
//        super(FindQuery.class, priority);
//        this.requiredPattern = Pattern.compile(regex.getRegex());
//    }

    public FindTypeCriterion(int priority, BsonType requiredType) {
        super(FindQuery.class, priority);
        this.requiredType = requiredType;
    }

    @Override
    protected void evaluate(FindQuery query) {
        currentScore = BsonDocumentChecker.containsValueOfType(query.getFilter(), requiredType) ? 1 : 0;
    }
}
