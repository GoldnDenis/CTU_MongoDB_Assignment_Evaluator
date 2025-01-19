package cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.composite.leaf.types.general;

import cz.cvut.fel.mongodb_assignment_evaluator.enums.Criteria;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.InsertedDocumentStorage;
import cz.cvut.fel.mongodb_assignment_evaluator.application.evaluation.checker.composite.leaf.CheckerLeaf;
import cz.cvut.fel.mongodb_assignment_evaluator.application.model.query.type.Query;

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
