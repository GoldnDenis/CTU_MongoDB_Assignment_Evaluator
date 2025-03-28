package cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.checker.composite.leaf.types.general;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.enums.Criteria;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type.Query;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.checker.InsertedDocumentStorage;
import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.checker.composite.leaf.CheckerLeaf;

public class CommentChecker extends CheckerLeaf<Query> {
    public CommentChecker(InsertedDocumentStorage documentStorage) {
        super(Criteria.COMMENT, Query.class, documentStorage);
    }

    @Override
    public void concreteCheck(Query query) {
        evaluationResult.setCriterionModifier(evaluationResult.getCriterionModifier() + 1);
        if (!query.getComment().isBlank()) {
            currentScore++;
        }
    }
}
