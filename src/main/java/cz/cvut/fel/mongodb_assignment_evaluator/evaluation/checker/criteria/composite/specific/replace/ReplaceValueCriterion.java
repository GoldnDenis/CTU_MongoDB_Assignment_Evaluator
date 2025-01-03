package cz.cvut.fel.mongodb_assignment_evaluator.evaluation.checker.criteria.composite.specific.replace;

import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.model.enums.Criteria;
import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.checker.InsertedDocumentStorage;
import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.checker.criteria.AssignmentCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.model.query.type.ReplaceOneQuery;

public class ReplaceValueCriterion extends AssignmentCriterion<ReplaceOneQuery> {
    public ReplaceValueCriterion(InsertedDocumentStorage documentStorage) {
        super(Criteria.REPLACE_VALUE, ReplaceOneQuery.class, documentStorage);
    }

    @Override
    public void concreteCheck(ReplaceOneQuery query) {
        if (documentStorage.findDocument(query.getCollection(), query.getFilter().getDocument()).isEmpty()) {
            return;
        }
        if (!query.getReplacement().isTrivial()) {
            currentScore++;
        }
    }
}
