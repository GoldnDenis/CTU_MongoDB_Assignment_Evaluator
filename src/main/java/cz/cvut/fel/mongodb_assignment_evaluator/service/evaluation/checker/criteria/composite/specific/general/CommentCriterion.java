package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.composite.specific.general;

import cz.cvut.fel.mongodb_assignment_evaluator.service.enums.Criteria;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.InsertedDocumentStorage;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.AssignmentCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.query.type.Query;

public class CommentCriterion extends AssignmentCriterion<Query> {
    public CommentCriterion(InsertedDocumentStorage documentStorage) {
        super(Criteria.COMMENT, Query.class, documentStorage);
    }

    @Override
    public void concreteCheck(Query query) {
        if (!query.getComment().isBlank()) {
            currentScore++;
        }
    }
}
