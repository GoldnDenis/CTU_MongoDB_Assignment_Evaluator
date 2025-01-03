package cz.cvut.fel.mongodb_assignment_evaluator.evaluation.checker.criteria.composite.specific.update.upsert;

import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.model.enums.Criteria;
import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.checker.InsertedDocumentStorage;
import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.checker.criteria.AssignmentCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.model.query.type.UpdateQuery;

public class UpdateUpsertUsedCriterion extends AssignmentCriterion<UpdateQuery> {
    public UpdateUpsertUsedCriterion(InsertedDocumentStorage documentStorage) {
        super(Criteria.UPDATE_UPSERT_USED, UpdateQuery.class, documentStorage);
    }

    @Override
    public void concreteCheck(UpdateQuery query) {
        if (query.upsertIsPositive()) {
            currentScore++;
        }
    }
}
