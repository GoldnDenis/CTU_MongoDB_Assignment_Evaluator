package cz.cvut.fel.mongodb_assignment_evaluator.evaluation.checker.criteria.composite.specific.general;

import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.model.enums.Criteria;
import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.checker.InsertedDocumentStorage;
import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.checker.criteria.AssignmentCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.model.query.type.Query;

public class CommentCriterion extends AssignmentCriterion<Query> {
    public CommentCriterion(InsertedDocumentStorage documentStorage) {
        super(Criteria.COMMENT, Query.class, documentStorage);
    }

    @Override
    public void concreteCheck(Query query) {
        criterionEvaluationResult.setCriterionModifier(criterionEvaluationResult.getCriterionModifier() + 1);
        if (!query.getComment().isBlank()) {
            currentScore++;
        }
    }
}
